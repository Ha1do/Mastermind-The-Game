package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Comment
{
    private String comment;
    private String player;
    private Date playedOn;

    public Comment(String comment, String player, Date playedOn)
    {
        this.comment = comment;
        this.player = player;
        this.playedOn = playedOn;
    }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getPlayer() { return player; }
    public void setPlayer(String player) { this.player = player; }

    public Date getPlayedOn() { return playedOn; }
    public void setPlayedOn(Date playedOn) { this.playedOn = playedOn; }
}
