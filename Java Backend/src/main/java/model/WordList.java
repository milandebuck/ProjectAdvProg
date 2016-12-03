package model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 3/12/2016.
 */

public class WordList {

    private String name;
    private List<ObjectId> entryList;

    public WordList() {
        this.entryList = new ArrayList<ObjectId>();
    }

    public WordList(String name, List<ObjectId> entryList) {
        this.name = name;
        this.entryList = entryList;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ObjectId> getEntryList() { return entryList; }
    public void setEntryList(List<ObjectId> entryList) { this.entryList = entryList; }
}
