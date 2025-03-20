package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Rating
{
    private String player;
    private int points;
    private Date playedOn;

    public Rating(String player, int points, Date playedOn)
    {
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }

    public String getPlayer() { return player; }
    public void setPlayer(String player) { this.player = player; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public Date getPlayedOn() { return playedOn; }
    public void setPlayedOn(Date playedOn) { this.playedOn = playedOn; }
}
