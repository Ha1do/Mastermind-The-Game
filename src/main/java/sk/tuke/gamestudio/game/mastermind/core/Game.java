package sk.tuke.gamestudio.game.mastermind.core;

import sk.tuke.gamestudio.game.mastermind.consoleui.ConsoleUI;
import sk.tuke.gamestudio.entity.User;

import java.util.Arrays;

public class Game
{
    private final int code_length;
    private final int[] secretCode;
    private boolean guessed;
    private int attempts;
    private final User user;
    private final ConsoleUI ui;

    public Game(int[] secretCode, User user, ConsoleUI console)
    {
        this.secretCode = secretCode;
        this.guessed = false;
        this.attempts = 0;
        this.user = user;
        this.code_length = secretCode.length;
        this.ui = console;
    }

    public void play ()
    {
        while (!guessed)
        {
            int[] guess = ui.attempGuess(code_length);
            checkGuess(guess);
        }
    }


    public void checkGuess(int[] guess) // console
    {
        attempts++;
        int[] numsCount = new int[10];
        char[] answer = new char[code_length];
        Arrays.fill(answer, 'N');
        for (int j : secretCode) numsCount[j]++;

        for (int i = 0; i < code_length; i++)
        {
            if (guess[i] == secretCode[i])
            {
                answer[i] = 'G';
                numsCount[guess[i]]--;
            }
        }

        for (int i = 0; i < code_length; i++)
        {
            if (answer[i] != 'G')
            {
                for (int j = 0; j < code_length; j++)
                {
                    if (guess[i] == secretCode[j] && i != j && numsCount[guess[i]] > 0)
                    {
                        answer[i] = 'Y';
                        numsCount[guess[i]]--;
                        break;
                    }
                }
            }
        }

        if (new String(answer).equals("G".repeat(code_length)))
        {
            guessed = true;

            int S = (int) ((code_length * Math.pow((50.0 / code_length), 1.2)) / attempts);
            user.setScore(S); // the more digits in the code, the more points you get
            return;
        }

        ui.feedBack(answer);
    }

    public boolean isGuessed() { return guessed; }

    public int getAttempts() { return attempts; }
// ============================================WEB====================================================
    public char[] checkGuessAndReturnFeedback(int[] guess) // WEB
    {
        attempts++;
        int[] numsCount = new int[10];
        char[] answer = new char[code_length];
        Arrays.fill(answer, 'N');
        for (int num : secretCode) numsCount[num]++;

        for (int i = 0; i < code_length; i++)
        {
            if (guess[i] == secretCode[i])
            {
                answer[i] = 'G';
                numsCount[guess[i]]--;
            }
        }

        for (int i = 0; i < code_length; i++)
        {
            if (answer[i] != 'G')
            {
                for (int j = 0; j < code_length; j++)
                {
                    if (guess[i] == secretCode[j] && i != j && numsCount[guess[i]] > 0)
                    {
                        answer[i] = 'Y';
                        numsCount[guess[i]]--;
                        break;
                    }
                }
            }
        }

        if (new String(answer).equals("G".repeat(code_length)))
        {
            guessed = true;

            int S = (int) ((code_length * Math.pow((50.0 / code_length), 1.2)) / attempts);
            user.setScore(S); // the more digits in the code, the more points you get
        }
        return answer;
    }
}