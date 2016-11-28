package App;

import db.EntryRepository;
import model.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for getting random wordlists from DB.
 * Created by Robbe De Geyndt on 16/11/2016.
 */

public class GetWordsResponse {

    private EntryRepository repository;
    private String[] languages;
    private int amount = 10;
    private List<Entry> words;

    public GetWordsResponse(EntryRepository repository, String[] languages, String amount) {
        this.languages = languages;
        this.words = new ArrayList<Entry>();

        try {
            this.amount = Integer.parseInt(amount);
        } catch (Exception e) {}

        int querySize = repository.findByLanguages(languages).size();

        for (int i = 0; i < this.amount; i++) {
            Random rnd = new Random();

            if (querySize >= 0) {
                int j = rnd.nextInt(repository.findAll().size());
                Entry doc = (Entry)(repository.findAll().toArray()[j]);
                words.add(doc);
            }
            else {
                int j = rnd.nextInt(repository.findByLanguages(languages).size());
                Entry doc = (Entry) (repository.findByLanguages(languages).toArray()[j]);
                words.add(doc);
            }
        }
    }

    public String[] getLanguages() {
        return languages;
    }

    public int getAmount() {
        return amount;
    }

    public List<Entry> getWords() {
        return words;
    }

    public boolean checkResponse(EntryRepository repository, int id, String answer){
        boolean correct = false;
        Entry entry = repository.findById(id);
        if(answer == entry.getTranslation()){
            correct = true;
        }
        return correct;
    }
}

