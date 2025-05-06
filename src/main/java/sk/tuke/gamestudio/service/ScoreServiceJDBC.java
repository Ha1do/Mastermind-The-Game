package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService {
    public static final String URL = "jdbc:postgresql://localhost/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT game, player, points, played_on FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";
    public static final String DELETE = "DELETE FROM score";
    public static final String INSERT = "INSERT INTO score (game, player, points, played_on) VALUES (?, ?, ?, ?)";

    @Override
    public void addScore(Score score) {
        String checkQuery = "SELECT points FROM score WHERE game = ? AND player = ?";
        String updateQuery = "UPDATE score SET points = ?, played_on = ? WHERE game = ? AND player = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            // Проверяем, есть ли уже запись для игрока в данной игре
            checkStatement.setString(1, score.getGame());
            checkStatement.setString(2, score.getPlayer());
            ResultSet rs = checkStatement.executeQuery();

            if (rs.next()) { // Если запись существует
                int currentPoints = rs.getInt("points");
                if (score.getPoints() > currentPoints) {
                    // Обновляем запись, если новый рекорд больше
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, score.getPoints());
                        updateStatement.setTimestamp(2, new Timestamp(score.getPlayedOn().getTime()));
                        updateStatement.setString(3, score.getGame());
                        updateStatement.setString(4, score.getPlayer());
                        updateStatement.executeUpdate();
                    }
                }
            } else {
                // Если записи нет, добавляем новую
                try (PreparedStatement insertStatement = connection.prepareStatement(INSERT)) {
                    insertStatement.setString(1, score.getGame());
                    insertStatement.setString(2, score.getPlayer());
                    insertStatement.setInt(3, score.getPoints());
                    insertStatement.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem adding score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                while (rs.next()) {
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting score", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }
    }
}