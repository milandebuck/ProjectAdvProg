package model;


import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class Result {
    private int score;
    private int max;
    private ObjectId list;
    private Date date;

    public Result() {
        this.date = new Date();
    }

    public Result (int score, int max, ObjectId list) {
        this.date = new Date();
        this.score = score;
        this.max = max;
        this.list = list;
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getMax() { return max; }
    public void getMax(int max) { this.max = max; }

    public ObjectId getList() { return list; }
    public void setListName(ObjectId list) { this.list = list; }

    public void resetDate() { date = new Date(); }
}
