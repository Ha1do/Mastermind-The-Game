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
        int[] numsCount = new int[10];
        char[] answer = {'N', 'N', 'N', 'N'};
        for (int j : secretCode) numsCount[j]++;

        for (int i = 0; i < CODE_LENGTH; i++)
        {
            if (guess[i] == secretCode[i])
            {
                answer[i] = 'G';
                numsCount[guess[i]]--;
            }
        }

        for (int i = 0; i < CODE_LENGTH; i++)
        {
            if (answer[i] != 'G')
            {
                for (int j = 0; j < CODE_LENGTH; j++)
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

        if (new String(answer).equals("GGGG"))
        {
            guessed = true;
            return;
        }

        System.out.print("Your guess is   : ");
        for (char match : answer)
        {
            switch (match)
            {
                case 'G' -> System.out.print("\u001B[32mG\u001B[0m");
                case 'Y' -> System.out.print("\u001B[33mY\u001B[0m");
                default -> System.out.print("\u001B[31mR\u001B[0m");

            }
        }
        System.out.println("\n");
    }

    public boolean isGuessed() { return guessed; }

    public int getAttempts() { return attempts; }
}
