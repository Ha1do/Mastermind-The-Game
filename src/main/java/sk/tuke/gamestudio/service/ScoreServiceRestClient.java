package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

@Service
public class ScoreServiceRestClient implements ScoreService
{
    private final String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;
//    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addScore(Score score) {
        // Получаем список всех очков игрока для игры
        List<Score> existingScores = Arrays.asList(
                restTemplate.getForEntity(url + "/" + score.getGame(), Score[].class).getBody());

        // Проверяем, есть ли у игрока запись
        Score existingScore = existingScores.stream()
                .filter(s -> s.getPlayer().equals(score.getPlayer()))
                .findFirst()
                .orElse(null);

        if (existingScore != null) {
            // Сравниваем текущий рекорд с новыми очками
            if (score.getPoints() > existingScore.getPoints()) {
                restTemplate.postForEntity(url, score, Score.class); // Обновляем рекорд
            }
        } else {
            restTemplate.postForEntity(url, score, Score.class); // Добавляем новую запись
        }
    }

    @Override
    public List<Score> getTopScores(String gameName)
    {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, Score[].class).getBody());
    }

    @Override
    public void reset()
    {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}