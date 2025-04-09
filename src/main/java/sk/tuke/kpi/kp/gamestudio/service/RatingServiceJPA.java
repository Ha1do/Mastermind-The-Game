package sk.tuke.kpi.kp.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.gamestudio.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException
    {
        entityManager.createNamedQuery("Rating.resetRatings")
                .setParameter("game", rating.getGame())
                .setParameter("player", rating.getPlayer())
                .executeUpdate();
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException
    {
        Double avg = entityManager.createNamedQuery("Rating.getAverageRating", Double.class)
                .setParameter("game", game)
                .getSingleResult();
        return avg != null ? avg.intValue() : 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException
    {
        return 0;
    }

    @Override
    public void reset() throws RatingException
    {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}