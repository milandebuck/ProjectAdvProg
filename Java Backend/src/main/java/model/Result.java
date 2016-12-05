package model;


import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Object that contains users score on a test.
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class Result {
    private int score;
    private int max;
    private ObjectId list;
    private Date date;

    /**
     * Empty constructor for Result object.
     */
    public Result() {
        this.date = new Date();
    }

    /**
     * Constructor for Result object
     * @param score earned score
     * @param max maximum score
     * @param list reference to the list that was tested.
     */
    public Result (int score, int max, ObjectId list) {
        this.date = new Date();
        this.score = score;
        this.max = max;
        this.list = list;
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }

    public ObjectId getList() { return list; }
    public void setListName(ObjectId list) { this.list = list; }

    public void resetDate() { date = new Date(); }
}
