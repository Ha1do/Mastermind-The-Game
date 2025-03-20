package main.java.sk.tuke.kpi.kp.mastermind.consoleui;

import java.util.Scanner;

public class UserInput
{
    private static final int CODE_LENGTH = 4;

    public static int[] getUserGuess(Scanner scanner)
    {
        while (true)
        {
            String input = scanner.nextLine();
            if (input.matches("[0-9]{4}"))
            {
                int[] guess = new int[CODE_LENGTH];
                for (int i = 0; i < CODE_LENGTH; i++)
                    guess[i] = Character.getNumericValue(input.charAt(i));
                return guess;
            }
            else
            {
                System.out.print("Wrong input. Please enter 4 digits from 0 to 9: \n");
                System.out.print("Enter your guess: ");
            }
        }
    }
}
