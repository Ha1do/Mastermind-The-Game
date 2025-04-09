package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceJDBC implements CommentService
{
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT id, game, player, comment, commented_on FROM comment WHERE game = ? ORDER BY commented_on DESC";
    public static final String DELETE = "DELETE FROM comment";
    public static final String INSERT = "INSERT INTO comment (id, game, player, comment, commented_on) VALUES (?, ?, ?, ?, ?)";

    @Override
    public void addComment(Comment comment)
    {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        )
        {
            statement.setInt(1, comment.getIdent());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getPlayer());
            statement.setString(4, comment.getComment());
            statement.setTimestamp(5, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e)
        {
            throw new CommentException("Problem inserting comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game)
    {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        )
        {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery())
            {
                List<Comment> comments = new ArrayList<>();
                while (rs.next())
                {
                    comments.add(new Comment(rs.getString(3), rs.getString(2), rs.getTimestamp(4), rs.getString(1)));
                }
                return comments;
            }
        } catch (SQLException e)
        {
            throw new CommentException("Problem selecting comments", e);
        }
    }

    @Override
    public void reset()
    {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        )
        {
            statement.executeUpdate(DELETE);
        } catch (SQLException e)
        {
            throw new CommentException("Problem deleting comments", e);
        }
    }
}