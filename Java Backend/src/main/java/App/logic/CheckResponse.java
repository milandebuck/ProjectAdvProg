package App.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.MongoConfig;
import model.Entry;
import model.Wrapper;
import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Class for checking list of answers.
 * Returns earned score, maximum score and correct solutions for wrong answers.
 * Created by Robbe De Geyndt on 26/11/2016.
 */

public class CheckResponse {

    private Wrapper input;
    private List<Object> entries;
    private Map<String,Object> answer;
    private int score;
    private List<Entry> faulty;
    private Wrapper result;

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

        invalid: try {

            //Get input
            ObjectMapper mapper = new ObjectMapper();
            input = mapper.readValue(object, Wrapper.class);

            //Check if valid
            if (!input.getSucces()) {
                result.setMsg("Incorrect input");
                break invalid;
            }
            
            //Get given enties
            entries = (List<Object>)input.getData();

            //check entries
            for (Object value : entries) {
                Entry entry = mapper.readValue(new JSONObject((HashMap<String,String>)value).toString(), Entry.class);
                Entry dbEntry = mongoOperations.findOne(new Query(where("word").is(entry.getWord())), Entry.class, "entries");
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
