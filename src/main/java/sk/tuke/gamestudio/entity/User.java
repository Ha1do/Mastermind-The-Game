package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "player")
@NamedQuery(name = "User.findByUsername",
        query = "SELECT u FROM User u WHERE u.username = :username")

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String username;
    private String email;
    private String password;
    private int score;
    private Date created_on;

    public User() {
        this.created_on = new Date();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.score = 0;
        this.created_on = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getCreatedOn() {
        return created_on;
    }

    public void setCreatedOn(Date created_on) {
        this.created_on = created_on;
    }
}