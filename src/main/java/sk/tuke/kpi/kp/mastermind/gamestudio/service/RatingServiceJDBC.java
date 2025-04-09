package sk.tuke.kpi.kp.mastermind.gamestudio.service;

import sk.tuke.kpi.kp.mastermind.gamestudio.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT rating FROM rating WHERE game = ?";
    public static final String SELECT_USER = "SELECT rating FROM rating WHERE game = ? AND player = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rating SET rating = ?, ratedOn = ? WHERE game = ? AND player = ?";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_USER)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE)) {
                        updateStatement.setInt(1, rating.getRating());
                        updateStatement.setTimestamp(2, new Timestamp(rating.getPlayedOn().getTime()));
                        updateStatement.setString(3, rating.getGame());
                        updateStatement.setString(4, rating.getPlayer());
                        updateStatement.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement(INSERT)) {
                        insertStatement.setString(1, rating.getGame());
                        insertStatement.setString(2, rating.getPlayer());
                        insertStatement.setInt(3, rating.getRating());
                        insertStatement.setTimestamp(4, new Timestamp(rating.getPlayedOn().getTime()));
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem setting rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_USER)
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new RatingException("No rating found for player " + player + " in game " + game);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                int sum = 0;
                int count = 0;
                while (rs.next()) {
                    sum += rs.getInt(1);
                    count++;
                }
                if (count == 0) {
                    throw new RatingException("No ratings found for game " + game);
                }
                return (int) ((double) sum / count);
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting average rating", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting ratings", e);
        }
    }
}