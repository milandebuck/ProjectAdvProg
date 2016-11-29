package App.logic;

import db.EntryRepository;
import model.Entry;
import model.Wrapper;

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
    private Wrapper listOut;

    /**
     * Empty constructor
     */
    public GetWordsResponse() {}

    /**
     * Constructor for making random word list.
     * @param repository Mongo repository for Entry objects.
     * @param languages Array of languages: to & from.
     * @param amount Size of list.
     */
    public GetWordsResponse(EntryRepository repository, String[] languages, String amount) {
        this.repository = repository;
        this.languages = languages;
        this.words = new ArrayList<>();
        this.listOut = new Wrapper();

        try {
            try {
                this.amount = Integer.parseInt(amount);
            } catch (Exception e) {
                listOut().setMsg(e.getMessage());
            }

            int querySize = repository.findByLanguages(languages).size();

            for (int i = 0; i < this.amount; i++) {
                Random rnd = new Random();

                //If languages not specified, use all.
                if (querySize >= 0) {
                    int j = rnd.nextInt(repository.findAll().size());
                    Entry doc = (Entry) (repository.findAll().toArray()[j]);
                    words.add(doc);
                } else {
                    int j = rnd.nextInt(repository.findByLanguages(languages).size());
                    Entry doc = (Entry) (repository.findByLanguages(languages).toArray()[j]);
                    words.add(doc);
                }

                //put in wrapper
                listOut().setData(words);
                listOut().setSucces(true);
            }
        } catch (Exception e) {

            //Give message of failure.
            listOut.setSucces(false);
            listOut.setMsg(e.getMessage());
        }

    }

    /**
     * Returns Wrapper object with response.
     * @return Wrapper
     */
    public Wrapper listOut() { return listOut; }
}

