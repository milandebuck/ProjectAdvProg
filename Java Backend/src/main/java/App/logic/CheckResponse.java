package App.logic;

import model.*;
import org.bson.types.ObjectId;
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

    //Configuration DB connection.
    private MongoOperations mongoOperations = Tools.getMongoOperations();

    private List<Entry> entries;
    private Map<String,Object> answer;
    private int score;
    private List<Entry> faulty;
    private Wrapper result;
    private List<Entry> dbEntries;
    private String username;
    private String[] languages;

    /**
     * Constructor, check if users answers are correct and gives back score.
     * @param username String containing username
     * @param object String containing request of user
     */
    public CheckResponse(String username, String object) {

        this.username = username;
        this.result = new Wrapper();
        this.answer = new HashMap<String, Object>();
        this.faulty = new ArrayList<Entry>();
        this.dbEntries = new ArrayList<Entry>();

        try {

            //Get get entries
            entries = Tools.jsonToArrayList(object);
            List<ObjectId> newList = new ArrayList<ObjectId>();


            //check entries
            for (Entry entry : entries) {

                if (languages == null) {
                    languages = entry.getLanguages();
                }
                else {
                    if ((!languages[0].equals(entry.getLanguages()[0])) && (!languages[1].equals(entry.getLanguages()[1]))) throw new Exception("Languages don't match");
                }

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

            //Write to database
            saveToDB(score, entries.size());

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
     * Save users earned score to the database.
     * @param score earned score
     * @param max maximum score
     */
    public void saveToDB(int score, int max) {
        List<String> lists = new ArrayList<>();

        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(username));

        User user = mongoOperations.findOne(getUser, User.class, "users");

        lists = user.getWordLists();

        ObjectId listId = Tools.exerciseIsList(lists, dbEntries);

        //if list exists save result.
        if (!(listId == null)) {
            user.addResult(new Result(score, max, listId, languages));
            mongoOperations.save(user,"users");
        }

        //if list doesn't exist create it and save result.
        else {
            List<ObjectId> ids = new ArrayList<ObjectId>();

            for (Entry entry : dbEntries) {
                ids.add(new ObjectId(entry.getId()));
            }

            WordList list = new WordList(ids);

            mongoOperations.save(list, "entries");

            user.addToWordLists(new ObjectId(list.getId()));
            user.addResult(new Result(score, max, new ObjectId(list.getId()), languages));
            mongoOperations.save(user, "users");
        }
    }

    /**
     * Returns Wrapper object with score and corrections for faulty answers.
     * @return Wrapper
     */
    public Wrapper getResult() {
        return result;
    }

}
