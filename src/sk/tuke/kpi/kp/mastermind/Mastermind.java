package sk.tuke.kpi.kp.mastermind;

import java.util.Scanner;

public class Mastermind
{
    private static final int CODE_LENGTH = 4;

    public static void main(String[] args)
    {
        UI ui = new UI();
        Scanner scanner = new Scanner(System.in);
        CodeGenerator codeGenerator = new CodeGenerator();
        Game game = new Game(codeGenerator.generateSecretCode());

        ui.printRules();

        while (!game.isGuessed())
        {
            System.out.print("Enter your guess: ");
            int[] guess = UserInput.getUserGuess(scanner);
            game.checkGuess(guess);
        }

        ui.win(game.getAttempts());
        scanner.close();
    }
}
