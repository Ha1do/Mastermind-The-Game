package sk.tuke.kpi.kp.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(r.points) FROM Rating r WHERE r.game=:game")
@NamedQuery(name = "Rating.resetRatings",
        query = "DELETE FROM Rating r WHERE r.game = :game AND r.player = :player")
public class Rating implements Serializable
{
    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String player;
    private int points;
    private Date playedOn;

    public Rating () {}

    public Rating(String player, int points, Date playedOn, String game)
    {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }

    public int getIdent()
    {
        return ident;
    }

    public void setIdent(int ident)
    {
        this.ident = ident;
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
        return points;
    }

    public void setRating(int points)
    {
        this.points = points;
    }

    public Date getPlayedOn()
    {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn)
    {
        this.playedOn = playedOn;
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
