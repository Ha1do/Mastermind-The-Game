package sk.tuke.kpi.kp.gamestudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.tuke.kpi.kp.gamestudio.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.gamestudio.core.CodeGenerator;
import sk.tuke.kpi.kp.gamestudio.core.Game;
import sk.tuke.kpi.kp.gamestudio.core.User;
import sk.tuke.kpi.kp.gamestudio.entity.Score;
import sk.tuke.kpi.kp.gamestudio.service.CommentServiceJDBC;
import sk.tuke.kpi.kp.gamestudio.service.RatingServiceJDBC;
import sk.tuke.kpi.kp.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;

@SpringBootApplication
public class Mastermind implements CommandLineRunner
{
    @Autowired
    private ConsoleUI ui;

    @Autowired
    private ScoreServiceJDBC scoreService;

    @Autowired
    private CommentServiceJDBC commentService;

    @Autowired
    private RatingServiceJDBC ratingService;

    public static void main(String[] args) {
        SpringApplication.run(Mastermind.class, args);
    }

    @Override
    public void run(String... args) {
        Date d = new Date();
        ui.Welcome();
        User user = new User();
        ui.AskForName(user);

        ui.seeComsRatsScores(scoreService, commentService, ratingService);

        CodeGenerator codeGenerator = new CodeGenerator(ui.getNumber());
        Game game = new Game(codeGenerator.generateSecretCode(), user, ui);

        game.play();
        ui.win(game.getAttempts(), user.getScore());

        Score score = new Score("Mastermind", user.getName(), user.getScore(), d);
        scoreService.addScore(score);
        ui.askForCommentRating(user.getName(), d, commentService, ratingService);

        ui.End();
    }
}
