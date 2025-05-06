package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    @PostMapping
public void addScore(@RequestBody Score score) {
    List<Score> existingScores = scoreService.getTopScores(score.getGame());

    // Проверяем, есть ли рекорд игрока
    Score existingScore = existingScores.stream()
            .filter(s -> s.getPlayer().equals(score.getPlayer()))
            .findFirst()
            .orElse(null);

    if (existingScore != null) {
        // Обновляем, если новый результат выше
        if (score.getPoints() > existingScore.getPoints()) {
            scoreService.addScore(score); // Используем существующий метод для обновления
        }
    } else {
        // Если записи игрока нет, добавляем
        scoreService.addScore(score);
    }
}
}