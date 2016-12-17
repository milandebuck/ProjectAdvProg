package App.logic;

import model.Entry;
import model.Wrapper;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for getting list of random entries from the database.
 * Created by Robbe De Geyndt on 16/11/2016.
 */

public class GetWordsResponse {

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private String[] languages;
    private int amount = 10;
    private List<Entry> words;
    private Wrapper listOut;
    private boolean reversed;

    /**
     * Empty constructor
     */
    public GetWordsResponse() {}

    /**
     * Constructor for making random word list.
     * @param languages array of languages: to and from
     * @param amount size of list.
     */
    public GetWordsResponse(String[] languages, String amount) {

        this.languages = languages;
        this.words = new ArrayList<>();
        this.listOut = new Wrapper();
        this.reversed = false;

        try {
            try {
                this.amount = Integer.parseInt(amount);
            } catch (Exception e) {
                listOut().setMsg(e.getMessage());
            }

            //Check straight
            Query queryLanguages = new Query();
            queryLanguages.addCriteria(Criteria.where("languages").is(languages));
            long querySize = mongoOperations.count(queryLanguages, Entry.class, "entries");

            //Check reverse
            if (querySize <= 0 && languages.length == 2) {
                String[] reLanguages = new String[]{languages[1], languages[0]};
                Query queryReLanguages = new Query();
                queryReLanguages.addCriteria(Criteria.where("languages").is(reLanguages));
                querySize = mongoOperations.count(queryReLanguages, Entry.class, "entries");

                //Set reversed flag and change query
                if (querySize > 0) {
                    reversed = true;
                    queryLanguages = queryReLanguages;
                }
            }

            //If no languages are provided use languages from random query.
            if (querySize <= 0) {
                Random random = new Random();

                //random Entry
                int j = random.nextInt((int)mongoOperations.count(new Query(), Entry.class, "entries"));
                Entry doc = (Entry) (mongoOperations.find(new Query().skip(j).limit(1), Entry.class, "entries").toArray()[0]);

                //random reverse
                reversed = random.nextBoolean();

                //Set languages
                languages = doc.getLanguages();

                //Set Query
                queryLanguages = new Query();
                queryLanguages.addCriteria(Criteria.where("languages").is(languages));

                //Set QuerySize
                querySize = mongoOperations.count(queryLanguages, Entry.class, "entries");
            }

            //Create test
            for (int i = 0; i < this.amount; i++) {
                Random rnd = new Random();

                int j = rnd.nextInt((int)querySize);
                Entry doc = (Entry) (mongoOperations.find(queryLanguages.skip(j).limit(1), Entry.class, "entries").toArray()[0]);

                //reversing
                if (reversed) doc = Tools.reverseEntry(doc);

                words.add(doc);
            }

            //put in wrapper
            listOut().setData(words);
            listOut().setSucces(true);

        } catch (Exception e) {

            //Give message of failure.
            listOut.setSucces(false);
            listOut.setMsg(e.toString());
        }

    }

    /**
     * Returns Wrapper object with response.
     * @return Wrapper
     */
    public Wrapper listOut() { return listOut; }
}

