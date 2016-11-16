package App;

import com.mongodb.DBCollection;
import db.MongoConnection;
import model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 16/11/2016.
 */
public class GetWordsResponse {

    private String language;
    private int amount;
    private List<Entry> words;
    private MongoConnection mongoConnection = new MongoConnection();

    public GetWordsResponse(String language, int amount) {
        this.language = language;
        this.amount = amount;
        this.words = new ArrayList<Entry>();

        for (int i = 0; i < amount; i++) {
            words.add(new Entry());
        }
    }

    public String getLanguage() {
        return language;
    }

    public int getAmount() {
        return amount;
    }

    public List<Entry> getWords() {
        return words;
    }

    public boolean checkAnswer(int passedId, String answer){
        Boolean correct = true;
        DBCollection collection = MongoConnection.Connect().getCollection("entries");
        collection.find();
        return correct;
    }
}
