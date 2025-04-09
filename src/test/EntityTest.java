package test;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.gamestudio.entity.Comment;
import sk.tuke.kpi.kp.gamestudio.entity.Rating;
import sk.tuke.kpi.kp.gamestudio.entity.Score;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {

    @Test
    public void testRating() {
        Date date = new Date();
        Rating rating = new Rating("player1", 5, date, "Mastermind");

        assertEquals("player1", rating.getPlayer());
        assertEquals(5, rating.getRating());
        assertEquals(date, rating.getPlayedOn());
        assertEquals("Mastermind", rating.getGame());

        rating.setPlayer("player2");
        rating.setRating(4);
        Date newDate = new Date();
        rating.setPlayedOn(newDate);
        rating.setGame("Chess");

        assertEquals("player2", rating.getPlayer());
        assertEquals(4, rating.getRating());
        assertEquals(newDate, rating.getPlayedOn());
        assertEquals("Chess", rating.getGame());
    }

    @Test
    public void testComment() {
        Date date = new Date();
        Comment comment = new Comment("Great game!", "player1", date, "Mastermind");

        assertEquals("Great game!", comment.getComment());
        assertEquals("player1", comment.getPlayer());
        assertEquals(date, comment.getCommentedOn());
        assertEquals("Mastermind", comment.getGame());

        comment.setComment("Not bad");
        comment.setPlayer("player2");
        Date newDate = new Date();
        comment.setCommentedOn(newDate);
        comment.setGame("Chess");

        assertEquals("Not bad", comment.getComment());
        assertEquals("player2", comment.getPlayer());
        assertEquals(newDate, comment.getCommentedOn());
        assertEquals("Chess", comment.getGame());
    }

    @Test
    public void testScore() {
        Date date = new Date();
        Score score = new Score("Mastermind", "player1", 100, date);

        assertEquals("Mastermind", score.getGame());
        assertEquals("player1", score.getPlayer());
        assertEquals(100, score.getPoints());
        assertEquals(date, score.getPlayedOn());

        score.setGame("Chess");
        score.setPlayer("player2");
        score.setPoints(200);
        Date newDate = new Date();
        score.setPlayedOn(newDate);

        assertEquals("Chess", score.getGame());
        assertEquals("player2", score.getPlayer());
        assertEquals(200, score.getPoints());
        assertEquals(newDate, score.getPlayedOn());
    }
}