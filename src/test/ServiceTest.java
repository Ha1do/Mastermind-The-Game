package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    private ScoreService scoreService;
    private RatingService ratingService;
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        scoreService = new ScoreServiceJDBC();
        ratingService = new RatingServiceJDBC();
        commentService = new CommentServiceJDBC();

        scoreService.reset();
        ratingService.reset();
        commentService.reset();
    }

    @Test
    public void testAddScore() {
        Score score = new Score("Mastermind", "player1", 100, new Date());
        scoreService.addScore(score);

        List<Score> scores = scoreService.getTopScores("Mastermind");
        assertEquals(1, scores.size());
        assertEquals("player1", scores.get(0).getPlayer());
        assertEquals(100, scores.get(0).getPoints());
    }

    @Test
    public void testGetTopScores() {
        scoreService.addScore(new Score("Mastermind", "player1", 100, new Date()));
        scoreService.addScore(new Score("Mastermind", "player2", 200, new Date()));

        List<Score> scores = scoreService.getTopScores("Mastermind");
        assertEquals(2, scores.size());
        assertEquals("player2", scores.get(0).getPlayer());
        assertEquals(200, scores.get(0).getPoints());
    }

    @Test
    public void testSetRating() {
        Rating rating = new Rating("player1", 5, new Date(), "Mastermind");
        ratingService.setRating(rating);

        int retrievedRating = ratingService.getRating("Mastermind", "player1");
        assertEquals(5, retrievedRating);
    }

    @Test
    public void testGetAverageRating() {
        ratingService.setRating(new Rating("player1", 5, new Date(), "Mastermind"));
        ratingService.setRating(new Rating("player2", 3, new Date(), "Mastermind"));

        int averageRating = ratingService.getAverageRating("Mastermind");
        assertEquals(4, averageRating);
    }

    @Test
    public void testAddComment() {
        Comment comment = new Comment("Great game!", "player1", new Date(), "Mastermind");
        commentService.addComment(comment);

        List<Comment> comments = commentService.getComments("Mastermind");
        assertEquals(1, comments.size());
        assertEquals("Great game!", comments.get(0).getComment());
    }

    @Test
    public void testGetComments() {
        commentService.addComment(new Comment("Great game!", "player1", new Date(), "Mastermind"));
        commentService.addComment(new Comment("Not bad", "player2", new Date(), "Mastermind"));

        List<Comment> comments = commentService.getComments("Mastermind");
        assertEquals(2, comments.size());
        assertEquals("Not bad", comments.get(0).getComment());
    }
}