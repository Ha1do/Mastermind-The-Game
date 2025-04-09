package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Comment.getComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.playedOn DESC")
@NamedQuery(name = "Comment.resetComments",
        query = "DELETE FROM Comment")
public class Comment implements Serializable
{
    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String comment;
    private String player;
    private Date playedOn;

    public Comment()
    {
    }

    public Comment(String comment, String player, Date playedOn, String game)
    {
        this.comment = comment;
        this.player = player;
        this.playedOn = playedOn;
        this.game = game;
    }

    public int getIdent()
    {
        return ident;
    }

    public void setIdent(int ident)
    {
        this.ident = ident;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getPlayer()
    {
        return player;
    }

    public void setPlayer(String player)
    {
        this.player = player;
    }

    public Date getCommentedOn()
    {
        return playedOn;
    }

    public void setCommentedOn(Date playedOn)
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
