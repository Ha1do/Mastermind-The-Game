package main.java.sk.tuke.kpi.kp.mastermind.consoleui;

import main.java.sk.tuke.kpi.kp.mastermind.core.User;

import java.util.Scanner;

public class ConsoleUI
{
    Scanner scanner = new Scanner(System.in);

    public void Welcome()
    {
        System.out.println("Welcome to \u001B[31mMASTERMIND\u001B[0m! Your task is to guess the secret code.");
        System.out.println("The secret code usually contains \u001B[34m4\u001B[0m digits.");
        System.out.println("But you can choose any number between \u001B[33m1\u001B[0m and \u001B[31m50\u001B[0m." +
                "(The recommended value is from \u001B[32m4\u001B[0m to \u001B[32m10\u001B[0m).");
        System.out.println("Digits can be repeated in the secret code. Like \u001B[34m1122\u001B[0m.");
        System.out.println("After each guess, you will receive feedback:");
        System.out.println("\u001B[32mG\u001B[0m (from \u001B[32mGreen\u001B[0m) - means that you have " +
                "\u001B[32mguessed\u001B[0m both, \u001B[4m\u001B[32mnumber\u001B[0m and its " +
                "\u001B[04m\u001B[32mposition\u001B[0m.");
        System.out.println("\u001B[33mY\u001B[0m (from \u001B[33mYellow\u001B[0m) - means that you " +
                "\u001B[33mguessed\u001B[0m the \u001B[32m\u001B[4mnumber\u001B[0m but not its " +
                "\u001B[31m\u001B[4mposition\u001B[0m.");
        System.out.println("\u001B[31mR\u001B[0m (from \u001B[31mRed\u001B[0m) - means that the " +
                "\u001B[31mnumber\u001B[0m is \u001B[4mnot in the secret code at all\u001B[0m.");
        System.out.println("Good luck!");
    }

    public void AskForName(User user)
    {
        System.out.print("Please enter your name here: ");
        user.setName(scanner.nextLine());
    }






    public void win(int attempts) {
        System.out.println("Congratulations! You guessed the secret code in " + attempts + " attempts.");
    }
}
