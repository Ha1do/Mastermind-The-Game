package sk.tuke.gamestudio;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.mastermind.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.mastermind.core.CodeGenerator;
import sk.tuke.gamestudio.game.mastermind.core.Game;
import sk.tuke.gamestudio.game.mastermind.core.User;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Date;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
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
    public ScoreService scoreServiceJPA() {
//        return new ScoreServiceJPA();
        return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService commentServiceJPA() {
//        return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }

    @Bean
    public RatingService ratingServiceJPA() {
//        return new RatingServiceJPA();
        return new RatingServiceRestClient();
    }

    @Bean
    public ConsoleUI consoleUI(@Qualifier("scoreServiceRestClient") ScoreService scoreService,
                               @Qualifier("commentServiceRestClient") CommentService commentService,
                               @Qualifier("ratingServiceRestClient") RatingService ratingService) {
        return new ConsoleUI(scoreService, commentService, ratingService);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}