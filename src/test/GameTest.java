package test;

import sk.tuke.kpi.kp.gamestudio.core.Game;
import sk.tuke.kpi.kp.gamestudio.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.gamestudio.core.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testCorrectGuess() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 1, 2};
        game.checkGuess(guess);
        assertTrue(game.isGuessed());
    }

    @Test
    public void testIncorrectGuess() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 1, 3};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testAttemptsCount() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess1 = {2, 2, 1, 3};
        int[] guess2 = {2, 2, 1, 2};
        game.checkGuess(guess1);
        game.checkGuess(guess2);
        assertEquals(2, game.getAttempts());
    }

    @Test
    public void testPartialCorrectGuess() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 1, 2, 2};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testNoCorrectGuess() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {3, 3, 3, 3};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testAllWrongPositions() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {1, 2, 2, 2};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testMixedCorrectAndIncorrectGuess() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 3, 1, 4};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testRepeatedDigitsInGuess() {
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 2, 2};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testRepeatedDigitsInSecretCode() {
        int[] secretCode = {2, 2, 2, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 2, 2};
        game.checkGuess(guess);
        assertTrue(game.isGuessed());
    }

    @Test
    public void testSingleDigitSecretCode() {
        int[] secretCode = {2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2};
        game.checkGuess(guess);
        assertTrue(game.isGuessed());
    }

    @Test
    public void testSingleDigitIncorrectGuess() {
        int[] secretCode = {2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {3};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }
}