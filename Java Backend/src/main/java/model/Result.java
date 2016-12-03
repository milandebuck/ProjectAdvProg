package model;


import java.util.Date;

/**
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class Result {
    private int score;
    private int max;
    private String listName;
    private Date date;

    public Result() {
        this.date = new Date();
    }

    public Result (int score, int max, String listName) {
        this.date = new Date();
        this.score = score;
        this.max = max;
        this.listName = listName;
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getMax() { return max; }
    public void getMax(int max) { this.max = max; }

    public String getListName() { return listName; }
    public void setListName(String listName) { this.listName = listName; }

    public void resetDate() { date = new Date(); }
}
