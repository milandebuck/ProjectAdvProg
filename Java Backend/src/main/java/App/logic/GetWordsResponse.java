package App.logic;

import config.MongoConfig;
import model.Entry;
import model.Wrapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for getting random wordlists from DB.
 * Created by Robbe De Geyndt on 16/11/2016.
 */

public class GetWordsResponse {

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
     * @param languages Array of languages: to & from.
     * @param amount Size of list.
     */
    public GetWordsResponse(String[] languages, String amount) {

        //Configuration DB-connection.
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");

        this.languages = languages;
        this.words = new ArrayList<>();
        this.listOut = new Wrapper();

        try {
            try {
                this.amount = Integer.parseInt(amount);
            } catch (Exception e) {
                listOut().setMsg(e.getMessage());
            }

            int querySize = mongoOperations.findAll(Entry.class).size();

            for (int i = 0; i < this.amount; i++) {
                Random rnd = new Random();

                //If languages not specified, use all.
                if (querySize >= 0) {
                    int j = rnd.nextInt(mongoOperations.findAll(Entry.class).size());
                    Entry doc = (Entry) (mongoOperations.findAll(Entry.class).toArray()[j]);
                    words.add(doc);
                } else {
                    //Set criteria
                    Query query = new Query();
                    query.addCriteria(Criteria.where("languages").is(languages));

                    int j = rnd.nextInt(mongoOperations.find(query, Entry.class, "entries").size());
                    Entry doc = (Entry) (mongoOperations.find(query, Entry.class, "entries").toArray()[j]);
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

