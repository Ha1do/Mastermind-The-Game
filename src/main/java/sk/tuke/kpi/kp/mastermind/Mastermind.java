package main.java.sk.tuke.kpi.kp.mastermind;

import main.java.sk.tuke.kpi.kp.mastermind.consoleui.ConsoleUI;
import main.java.sk.tuke.kpi.kp.mastermind.core.CodeGenerator;
import main.java.sk.tuke.kpi.kp.mastermind.core.Game;
import main.java.sk.tuke.kpi.kp.mastermind.core.User;

public class Mastermind
{
    public static void main(String[] args)
    {
        ConsoleUI ui = new ConsoleUI();
        ui.Welcome();
        User user = new User ();
        ui.AskForName(user);

        CodeGenerator codeGenerator = new CodeGenerator(ui.getNumber());
        Game game = new Game(codeGenerator.generateSecretCode(), user, ui);
        game.play();

        ui.win(game.getAttempts());
    }
}
