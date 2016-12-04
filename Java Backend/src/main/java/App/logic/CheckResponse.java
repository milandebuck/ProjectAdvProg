package App.logic;

import App.configuration.MongoConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Entry;
import model.Wrapper;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for checking list of answers.
 * Returns earned score, maximum score and correct solutions for wrong answers.
 * Created by Robbe De Geyndt on 26/11/2016.
 */

public class CheckResponse {

    private List<Entry> entries;
    private Map<String,Object> answer;
    private int score;
    private List<Entry> faulty;
    private Wrapper result;
    private List<Entry> dbEntries;

    /**
     * Constructor, check if users answers are correct and gives back score.
     * @param object String containing request of user.
     */
    public CheckResponse(String object) {

        //Configuration DB connection.
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");

        this.result = new Wrapper();
        this.answer = new HashMap<String, Object>();
        this.faulty = new ArrayList<Entry>();
        this.dbEntries = new ArrayList<Entry>();

        try {

            Wrapper input = new Wrapper();

            //Json to Wrapper
            ObjectMapper mapper = new ObjectMapper();
            input = mapper.readValue(object, Wrapper.class);

            //Check if valid
            if (!input.getSucces()) {
                throw new Exception("No valid input");
            }

            //Get get entries
            entries = JsonToList.convert(JSONObject.valueToString(input.getData()));
            List<ObjectId> newList = new ArrayList<ObjectId>();


            //check entries
            for (Entry entry : entries) {
                Query getWord = new Query();
                getWord.addCriteria(Criteria.where("word").is(entry.getWord()).and("languages").is(entry.getLanguages()));

                Entry dbEntry = mongoOperations.findOne(getWord, Entry.class, "entries");
                dbEntries.add(dbEntry);

                if (dbEntry.getTranslation().equals(entry.getTranslation())) {
                    score++;
                }
                else {
                    faulty.add(dbEntry);
                }
            }



            //Make answer
            answer.put("score", score);
            answer.put("max", entries.size());
            answer.put("faulty", faulty);

            //put in wrapper
            result.setData(answer);
            result.setSucces(true);

        } catch (Exception e) {
            //Give message on failure
            result.setSucces(false);
            result.setMsg(e.getMessage());
        }
    }

    /**
     * Returns Wrapper object with score & corrections for faulty answers.
     * @return Wrapper
     */
    public Wrapper getResult() {
        return result;
    }

}
