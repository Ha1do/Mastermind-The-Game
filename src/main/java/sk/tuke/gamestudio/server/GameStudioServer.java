package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "sk.tuke.gamestudio.entity")
@ComponentScan(basePackages = "sk.tuke.gamestudio")
public class GameStudioServer {
    private static final Logger logger = LoggerFactory.getLogger(GameStudioServer.class);

    public static void main(String[] args) {
        logger.info("Запуск сервера GameStudio...");
        SpringApplication.run(GameStudioServer.class, args);
        logger.info("Сервер успешно запущен.");
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService()
    {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService()
    {
        return new RatingServiceJPA();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}