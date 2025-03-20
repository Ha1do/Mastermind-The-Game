package main.java.sk.tuke.kpi.kp.mastermind.core;

public class User
{
    private String name;
    private int score;

    public User ()
    {
        this.name = "";
        this.score = 0;
    }

    public void setName (String name) { this.name = name; }
    public String getName() { return name; }

    public void setScore (int score) { this.score = score; }
    public int getScore() { return score; }
}
