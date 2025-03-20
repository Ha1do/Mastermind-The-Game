package main.java.sk.tuke.kpi.kp.mastermind;

import main.java.sk.tuke.kpi.kp.mastermind.consoleui.ConsoleUI;
import main.java.sk.tuke.kpi.kp.mastermind.core.CodeGenerator;
import main.java.sk.tuke.kpi.kp.mastermind.core.Game;
import main.java.sk.tuke.kpi.kp.mastermind.core.User;
import main.java.sk.tuke.kpi.kp.mastermind.consoleui.UserInput;

public class Mastermind
{
    public static void main(String[] args)
    {
//        Scanner scanner = new Scanner(System.in);
        ConsoleUI ui = new ConsoleUI();


        ui.Welcome();
        User user = new User ();
        ui.AskForName(user);





        CodeGenerator codeGenerator = new CodeGenerator();
        Game game = new Game(codeGenerator.generateSecretCode(), user);



        while (!game.isGuessed())
        {
            System.out.print("Enter your guess: ");
            int[] guess = UserInput.getUserGuess(scanner);
            game.checkGuess(guess);
        }

        ui.win(game.getAttempts());
        scanner.close();

        System.out.println("The " + user.getName() + " score is: " + user.getScore());

    }
}
