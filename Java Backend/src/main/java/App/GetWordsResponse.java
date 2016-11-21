package App;

import db.EntryRepository;
import model.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Robbe De Geyndt on 16/11/2016.
 */

public class GetWordsResponse {

    private EntryRepository repository;
    private String language;
    private int amount;
    private List<Entry> words;

    public GetWordsResponse(EntryRepository repository, String language, int amount) {
        this.language = language;
        this.amount = amount;
        this.words = new ArrayList<>();


        for (int i = 0; i < amount; i++) {
            Random rnd = new Random();
            int j = rnd.nextInt((int)repository.count());
            Entry doc = (Entry)(repository.findAll().toArray()[j]);
            words.add(doc);
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
}

