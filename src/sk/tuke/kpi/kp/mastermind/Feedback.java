package sk.tuke.kpi.kp.mastermind;

public class Feedback
{
    private static final int CODE_LENGTH = 4;

    public static int[] countMatches(int[] code, int[] guess)
    {
        int[] answer = new int[CODE_LENGTH];

        for (int i = 0; i < CODE_LENGTH; i++)
        {
            for (int j = 0; j < CODE_LENGTH; j++)
            {
                if (guess[i] == code[j] && i == j)
                {
                    answer[i] = 5; // Полное совпадение (G)
                    break;
                }
                else if (guess[i] == code[j])
                    answer[i] = 1; // Частичное совпадение (Y)
            }
        }
        return answer;
    }

    public static boolean isFullyGuessed(int[] feedback)
    {
        for (int value : feedback)
        {
            if (value != 5)
                return false;
        }
        return true;
    }
}
