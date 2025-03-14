import java.util.*;

public class Mastermind
{
    private static final int CODE_LENGTH = 4;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int[] secretCode = generateSecretCode();
        System.out.println("Добро пожаловать в игру Мастермайнд!");

        boolean guessed = false;
        int attempts = 0;

        while (!guessed)
        {
            System.out.print("Введите вашу догадку (4 цифры от 1 до 6): ");
            int[] guess = getUserGuess(scanner);
            attempts++;

            int[] matches = countMatches(secretCode, guess);

            int count = 0;
            for (int k : matches)
            {
                if (k == 5)
                    count++;
            }
            if (count == 4)
                guessed = true;

            if (guessed)
                break;

            for (int match : matches)
            {
                if (match == 5)
                    System.out.print("GG ");
                else if (match > 0)
                    System.out.print("G ");
                else
                    System.out.print("N ");
            }
        }
        System.out.println("\nПоздравляем! Вы угадали код " + Arrays.toString(secretCode) + " за " + attempts + " попыток.");
        scanner.close();
    }

    private static int[] generateSecretCode()
    {
        Random rand = new Random();
        int[] code = new int[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++)
        {
            code[i] = rand.nextInt(10);
        }
        return code;
    }

    private static int[] getUserGuess(Scanner scanner)
    {
        while (true) {
            String input = scanner.nextLine();
            if (input.matches("[0-9]{4}"))
            {
                int[] guess = new int[CODE_LENGTH];
                for (int i = 0; i < CODE_LENGTH; i++)
                {
                    guess[i] = Character.getNumericValue(input.charAt(i));
                }
                return guess;
            } else {
                System.out.print("Неверный ввод. Введите 4 цифры от 1 до 6: ");
            }
        }
    }

    private static int[] countMatches(int[] code, int[] guess)
    {
        int[] answer = new int[CODE_LENGTH];

        // counting guessed nums but wrong position
        for (int i = 0; i < CODE_LENGTH; i++)
        {
            for (int j = 0; j < CODE_LENGTH; j++)
            {
                if (guess[i] == code[j])
                {
                    if (i == j)
                    {
                        answer[i] = 5;
                        continue;
                    }
                    answer[i]++;
                }
            }
        }
        return answer;
    }

    private static int countPartialMatches(int[] code, int[] guess) {
        int count = 0;
        Map<Integer, Integer> codeFrequency = new HashMap<>();
        Map<Integer, Integer> guessFrequency = new HashMap<>();

        for (int i = 0; i < CODE_LENGTH; i++) {
            if (code[i] != guess[i]) {
                codeFrequency.put(code[i], codeFrequency.getOrDefault(code[i], 0) + 1);
                guessFrequency.put(guess[i], guessFrequency.getOrDefault(guess[i], 0) + 1);
            }
        }

        for (int key : guessFrequency.keySet()) {
            if (codeFrequency.containsKey(key)) {
                count += Math.min(codeFrequency.get(key), guessFrequency.get(key));
            }
        }

        return count;
    }
}
