package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This is our mongodocument for translations
 */


@Document(collection = "entries")
public class Entry {

    @Id
    private String id;

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

    public String ToString() {
        return this.id + ": word = " + word + ", translation = " + translation;
    }
    //TODO: ToString

}
