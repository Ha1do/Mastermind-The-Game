package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Comment.getComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.commented_on DESC")
@NamedQuery(name = "Comment.resetComments",
        query = "DELETE FROM Comment")
public class Comment implements Serializable
{
    @Id
    @GeneratedValue
    private int id;

    private String game;
    private String comment;
    private String player;
    private Date commented_on;

    public Comment()
    {
    }

    public Comment(String comment, String player, Date commented_on, String game)
    {
        this.comment = comment;
        this.player = player;
        this.commented_on = commented_on;
        this.game = game;
    }

    public int getIdent()
    {
        return id;
    }

    public void setIdent(int id)
    {
        this.id = id;
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
        return commented_on;
    }

    public void setCommentedOn(Date commented_on)
    {
        this.commented_on = commented_on;
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
