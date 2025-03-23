package main.java.sk.tuke.kpi.kp.mastermind;

import main.java.sk.tuke.kpi.kp.mastermind.consoleui.ConsoleUI;
import main.java.sk.tuke.kpi.kp.mastermind.core.CodeGenerator;
import main.java.sk.tuke.kpi.kp.mastermind.core.Game;
import main.java.sk.tuke.kpi.kp.mastermind.core.User;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;

public class Mastermind
{
    public static void main(String[] args)
    {
        ScoreServiceJDBC scoreService = new ScoreServiceJDBC();
        CommentServiceJDBC commentService = new CommentServiceJDBC();
        RatingServiceJDBC ratingService = new RatingServiceJDBC();

        Date d = new Date();
        ConsoleUI ui = new ConsoleUI();
        ui.Welcome();
        User user = new User();
        ui.AskForName(user);

        ui.seeComsRatsScores(scoreService, commentService, ratingService);

        CodeGenerator codeGenerator = new CodeGenerator(ui.getNumber());
        Game game = new Game(codeGenerator.generateSecretCode(), user, ui);

        game.play();
        ui.win(game.getAttempts());

        Score score = new Score("Mastermind", user.getName(), user.getScore(), d);
        scoreService.addScore(score);
        ui.askForCommentRating(user.getName(), d, commentService, ratingService);

        ui.End();
    }
}
