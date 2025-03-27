package test.java.sk.tuke.kpi.kp.mastermind;

import main.java.sk.tuke.kpi.kp.mastermind.consoleui.ConsoleUI;
import main.java.sk.tuke.kpi.kp.mastermind.core.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class ConsoleUITest {

    private ConsoleUI consoleUI;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() {
        consoleUI = new ConsoleUI();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testWelcome() {
        consoleUI.Welcome();
        String output = outContent.toString();
        assertTrue(output.contains("Welcome to"));
        assertTrue(output.contains("MASTERMIND"));
    }

    @Test
    public void testFeedBack() {
        char[] answer = {'G', 'Y', 'R', 'G'};
        consoleUI.feedBack(answer);
        String output = outContent.toString();
        assertTrue(output.contains("\u001B[32mG\u001B[0m"));
        assertTrue(output.contains("\u001B[33mY\u001B[0m"));
        assertTrue(output.contains("\u001B[31mR\u001B[0m"));
    }

    @Test
    public void testWin() {
        consoleUI.win(5, 100);
        String output = outContent.toString();
        assertTrue(output.contains("Congratulations! You guessed the secret code in 5 attempts and you got 100 points."));
    }
}