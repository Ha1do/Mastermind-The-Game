package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) throws ScoreException {
        try {
            // Проверяем, есть ли уже запись для игрока в этой игре
            List<Score> existingScores = entityManager.createQuery(
                    "SELECT s FROM Score s WHERE s.game = :game AND s.player = :player", Score.class)
                    .setParameter("game", score.getGame())
                    .setParameter("player", score.getPlayer())
                    .getResultList();

            if (!existingScores.isEmpty()) {
                Score existingScore = existingScores.get(0);
                // Сравниваем старый результат с новым
                if (score.getPoints() > existingScore.getPoints()) {
                    existingScore.setPoints(score.getPoints());
                    existingScore.setPlayedOn(score.getPlayedOn());
                    entityManager.merge(existingScore);
                }
            } else {
                // Если записи нет, создаем новую
                entityManager.persist(score);
            }
        } catch (Exception e) {
            throw new ScoreException("Problem adding score via JPA", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException
    {
        return entityManager.createNamedQuery("Score.getTopScores", Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() throws ScoreException
    {
        entityManager.createNamedQuery("Score.resetScores").executeUpdate();
    }
}