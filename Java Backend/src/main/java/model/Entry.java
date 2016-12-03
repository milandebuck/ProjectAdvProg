package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This is our mongodocument for translations
 */


@JsonSerialize
@Document(collection = "entries")
public class Entry {


    @Id
    private ObjectId id;

    private String word;
    private String translation;
    private String[] languages = new String[2];

    public Entry() {
        this.word = "";
        this.translation = "";
    }

    public Entry(String w, String t,String[] l) {
        this.word = w;
        this.translation = t;
        this.languages = l;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String[] getLanguages(){
        return this.languages;
    }

    @JsonDeserialize
    public ObjectId getId() { return id; }

    public String ToString() {
        return this.id + ": word = " + word + ", translation = " + translation;
    }
    //TODO: ToString

}
