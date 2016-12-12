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
 * Object that contains a list of entries (test).
 * Created by Robbe De Geyndt on 3/12/2016.
 */
@Document(collection = "entries")
public class WordList {

    @Id
    @JsonTypeId
    private ObjectId id;
    private String name;
    private List<ObjectId> entryList;

    /**
     * Empty constructor of WordList object.
     */
    public WordList() {
        this.entryList = new ArrayList<ObjectId>();
    }

    /**
     * Constructor of WordList object, with standard name.
     * @param entryList list of entries for test
     */
    public WordList(List<ObjectId> entryList) {
        this.name = new SimpleDateFormat("EEEE d MMMM yyyy - HH:mm").format(new Date());
        this.entryList = entryList;
    }

    /**
     * Constructor of WordList object.
     * @param name name for test
     * @param entryList list of entries for test
     */
    public WordList(String name, List<ObjectId> entryList) {
        this.name = name;
        this.entryList = entryList;
    }

    public String getId() { return id.toHexString(); }
    public void setId(ObjectId id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getEntryList() {
        List<String> stringIds = new ArrayList<>();

        for (ObjectId objId : this.entryList) stringIds.add(objId.toHexString());
        return stringIds;
    }
    public void setEntryList(List<ObjectId> entryList) { this.entryList = entryList; }

}
