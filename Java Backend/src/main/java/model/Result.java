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
    private String[] languages;

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
     * @param list reference to the list that was tested
     * @param languages languages used in test
     */
    public Result (int score, int max, ObjectId list, String[] languages) {
        this.date = new Date();
        this.score = score;
        this.max = max;
        this.list = list;
        this.languages = languages;
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }

    public String getList() { return list.toHexString(); }
    public void setList(ObjectId list) { this.list = list; }

    public Date getDate() { return this.date; }
    public void resetDate() { date = new Date(); }

    public String[] getLanguages() { return languages; }
    public void setLanguages(String[] languages) { this.languages = languages; }
}
