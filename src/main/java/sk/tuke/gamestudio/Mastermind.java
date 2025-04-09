package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.mastermind.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.mastermind.core.CodeGenerator;
import sk.tuke.gamestudio.game.mastermind.core.Game;
import sk.tuke.gamestudio.game.mastermind.core.User;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Date;

@SpringBootApplication
@Configuration
public class Mastermind
{

    public static void main(String[] args) {
        new SpringApplicationBuilder(Mastermind.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }


    @Bean
    public CommandLineRunner runner(ConsoleUI ui)
    {
        return args ->
        {
            Date d = new Date();
            ui.Welcome();
            User user = new User();
            ui.AskForName(user);

            ui.seeComsRatsScores(ui.getScoreService(), ui.getCommentService(), ui.getRatingService());

            CodeGenerator codeGenerator = new CodeGenerator(ui.getNumber());
            Game game = new Game(codeGenerator.generateSecretCode(), user, ui);

            game.play();
            ui.win(game.getAttempts(), user.getScore());

            Score score = new Score("Mastermind", user.getName(), user.getScore(), d);
            ui.getScoreService().addScore(score);
            ui.askForCommentRating(user.getName(), d, ui.getCommentService(), ui.getRatingService());

            ui.End();
        };
    }

    @Bean
    public ScoreServiceJPA scoreServiceJPA() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentServiceJPA commentServiceJPA() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingServiceJPA ratingServiceJPA() {
        return new RatingServiceJPA();
    }

    @Bean
    public ConsoleUI consoleUI(ScoreServiceJPA scoreService, CommentServiceJPA commentService, RatingServiceJPA ratingService) {
        return new ConsoleUI(scoreService, commentService, ratingService);
    }
}