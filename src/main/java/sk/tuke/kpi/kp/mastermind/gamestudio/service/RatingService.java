package sk.tuke.kpi.kp.mastermind.gamestudio.service;

import sk.tuke.kpi.kp.mastermind.gamestudio.entity.Rating;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    int getRating(String game, String player) throws RatingException;
    void reset() throws RatingException;
}
