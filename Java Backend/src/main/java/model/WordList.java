package model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 3/12/2016.
 */
@Document(collection = "entries")
public class WordList {

    @Id
    @JsonTypeId
    private ObjectId id;
    private String name;
    private List<ObjectId> entryList;

    public WordList() {
        this.entryList = new ArrayList<ObjectId>();
    }

    public WordList(List<ObjectId> entryList) {
        this.name = new SimpleDateFormat("EEEE d MMMM yyyy - HH:mm").format(new Date());
        this.entryList = entryList;
    }

    public WordList(String name, List<ObjectId> entryList) {
        this.name = name;
        this.entryList = entryList;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ObjectId> getEntryList() { return entryList; }
    public void setEntryList(List<ObjectId> entryList) { this.entryList = entryList; }
}
