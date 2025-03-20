package test.java.sk.tuke.kpi.kp.mastermind;

import main.java.sk.tuke.kpi.kp.mastermind.core.Game;
import main.java.sk.tuke.kpi.kp.mastermind.consoleui.ConsoleUI;
import main.java.sk.tuke.kpi.kp.mastermind.core.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testCorrectGuess() {
        System.out.println("test1");
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 1, 2};
        game.checkGuess(guess);
        assertTrue(game.isGuessed());
    }

    @Test
    public void testIncorrectGuess() {
        System.out.println("test2");
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 1, 3};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testAttemptsCount() {
        System.out.println("test3");
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
        System.out.println("test4");
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 1, 2, 2};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testNoCorrectGuess() {
        System.out.println("test5");
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {3, 3, 3, 3};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testAllWrongPositions() {
        System.out.println("test6");
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {1, 2, 2, 2};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    // !!! Due to the way the game is implemented, this test will fail, as the game will not allow for:
    // an empty guess
    // a guess that is shorter than the secret code
    // a guess that is longer than the secret code

//    @Test
//    public void testEmptyGuess() {
//        int[] secretCode = {2, 2, 1, 2};
//        Game game = new Game(secretCode, new User(), new ConsoleUI());
//        int[] guess = {};
//        game.checkGuess(guess);
//        assertFalse(game.isGuessed());
//    }

//    @Test
//    public void testShortGuess() {
//        int[] secretCode = {2, 2, 1, 2};
//        Game game = new Game(secretCode, new User(), new ConsoleUI());
//        int[] guess = {2, 2};
//        game.checkGuess(guess);
//        assertFalse(game.isGuessed());
//    }

//    @Test
//    public void testLongGuess() {
//        int[] secretCode = {2, 2, 1, 2};
//        Game game = new Game(secretCode, new User(), new ConsoleUI());
//        int[] guess = {2, 2, 1, 2, 3};
//        game.checkGuess(guess);
//        assertFalse(game.isGuessed());
//    }

    @Test
    public void testMixedCorrectAndIncorrectGuess() {
        System.out.println("test7");
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 3, 1, 4};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testRepeatedDigitsInGuess() {
        System.out.println("test8");
        int[] secretCode = {2, 2, 1, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 2, 2};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }

    @Test
    public void testRepeatedDigitsInSecretCode() {
        System.out.println("test9");
        int[] secretCode = {2, 2, 2, 2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2, 2, 2, 2};
        game.checkGuess(guess);
        assertTrue(game.isGuessed());
    }

    @Test
    public void testSingleDigitSecretCode() {
        System.out.println("test10");
        int[] secretCode = {2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {2};
        game.checkGuess(guess);
        assertTrue(game.isGuessed());
    }

    @Test
    public void testSingleDigitIncorrectGuess() {
        System.out.println("test11");
        int[] secretCode = {2};
        Game game = new Game(secretCode, new User(), new ConsoleUI());
        int[] guess = {3};
        game.checkGuess(guess);
        assertFalse(game.isGuessed());
    }
}