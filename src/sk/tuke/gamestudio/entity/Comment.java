package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Comment
{
    private String game;
    private String comment;
    private String player;
    private Date playedOn;

    public Comment(String comment, String player, Date playedOn, String game)
    {
        this.comment = comment;
        this.player = player;
        this.playedOn = playedOn;
        this.game = game;
    }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getPlayer() { return player; }
    public void setPlayer(String player) { this.player = player; }

    public Date getCommentedOn() { return playedOn; }
    public void setCommentedOn(Date playedOn) { this.playedOn = playedOn; }

    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }
}
