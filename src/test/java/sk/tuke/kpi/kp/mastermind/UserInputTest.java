package test.java.sk.tuke.kpi.kp.mastermind;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UserInputTest {

    @Test
    public void testGetUserGuessValidInput() {
        String input = "1234\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        Scanner scanner = new Scanner(System.in);

        int[] expectedGuess = {1, 2, 3, 4};
        int[] actualGuess = UserInput.getUserGuess(scanner);

        assertArrayEquals(expectedGuess, actualGuess);
    }

    @Test
    public void testGetUserGuessInvalidInput() {
        String input = "abcd\n1234\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        Scanner scanner = new Scanner(System.in);

        int[] expectedGuess = {1, 2, 3, 4};
        int[] actualGuess = UserInput.getUserGuess(scanner);

        assertArrayEquals(expectedGuess, actualGuess);
    }
}