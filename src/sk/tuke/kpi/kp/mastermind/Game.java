package sk.tuke.kpi.kp.mastermind;

public class Game
{
    private static final int CODE_LENGTH = 4;
    private final int[] secretCode;
    private boolean guessed;
    private int attempts;

    public Game(int[] secretCode)
    {
        this.secretCode = secretCode;
        this.guessed = false;
        this.attempts = 0;
    }

    public void checkGuess(int[] guess)
    {
        attempts++;
        int[] feedback = Feedback.countMatches(secretCode, guess);

        if (Feedback.isFullyGuessed(feedback))
        {
            guessed = true;
            return;
        }

        System.out.print("Your guess is   : ");
        for (int match : feedback)
        {
            if (match == 5)
                System.out.print("\u001B[32mG\u001B[0m");
            else if (match > 0)
                System.out.print("\u001B[33mY\u001B[0m");
            else
                System.out.print("\u001B[31mR\u001B[0m");
        }
        System.out.println("\n");
    }

    public boolean isGuessed() { return guessed; }

    public int getAttempts() { return attempts; }
}
