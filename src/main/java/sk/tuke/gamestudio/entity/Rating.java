package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game=:game")
@NamedQuery(name = "Rating.resetRatings",
        query = "DELETE FROM Rating r WHERE r.game = :game AND r.player = :player")
public class Rating implements Serializable
{
    @Id
    @GeneratedValue
    private int id;

    private String game;
    private String player;
    private int rating;
    private Date rated_on;

    public Rating () {}

    public Rating(String player, int rating, Date rated_on, String game)
    {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.rated_on = rated_on;
    }

    public int getIdent()
    {
        return id;
    }

    public void setIdent(int id)
    {
        this.id = id;
    }

    public String getPlayer()
    {
        return player;
    }

    public void setPlayer(String player)
    {
        this.player = player;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int points)
    {
        this.rating = points;
    }

    public Date getPlayedOn()
    {
        return rated_on;
    }

    public void setPlayedOn(Date playedOn)
    {
        this.rated_on = playedOn;
    }

    public String getGame()
    {
        return game;
    }

    public void setGame(String game)
    {
        this.game = game;
    }
}
