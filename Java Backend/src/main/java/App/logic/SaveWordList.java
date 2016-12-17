package App.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Entry;
import model.User;
import model.WordList;
import model.Wrapper;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Save WordLists to the database.
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class SaveWordList {

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private Wrapper result;
    private List<Entry> entries;
    private String[] languages;

    /**
     * Object saves list of entries (test) to database.
     * @param username user's screen name
     * @param object request of user
     */
    public SaveWordList(String username, String object) {

        result = new Wrapper();

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, String> input = objectMapper.readValue(object, HashMap.class);

            //Get get names
            String name = input.get("name");
            String inputMap = JSONObject.valueToString(input.get("list"));

            entries = new Converter<Entry>().jsonToArrayList(inputMap, Entry.class);
            List<ObjectId> newList = new ArrayList<ObjectId>();

            Query checkUser = new Query();
            checkUser.addCriteria(Criteria.where("username").is(username));
            User user = mongoOperations.findOne(checkUser, User.class, "users");

            for (Entry entry : entries) {

                if (languages == null) {
                    languages = entry.getLanguages();
                }
                else {
                    if ((!languages[0].equals(entry.getLanguages()[0])) && (!languages[1].equals(entry.getLanguages()[1]))) throw new Exception("Languages don't match");
                }

                Query checkEntry = new Query();
                checkEntry.addCriteria(Criteria.where("word").is(entry.getWord()).and("translation").is(entry.getTranslation()).and("languages").is(entry.getLanguages()));

                //in DB?
                Entry dbEntry = Tools.checkIfInDB(entry, true);

                //reverse in DB?
                if (dbEntry == null) {
                    dbEntry = Tools.checkIfInDB(Tools.reverseEntry(entry), true);
                }

                //new entry?
                if (dbEntry == null) {
                    Tools.teacherCheck(user);

                    //Check straight.
                    Query check = new Query();
                    check.addCriteria(Criteria.where("languages").is(entry.getLanguages()));
                    long count = mongoOperations.count(check, Entry.class, "entries");

                    //Check reversed
                    if (count <= 0) {
                        check = new Query();
                        entry = Tools.reverseEntry(entry);
                        check.addCriteria(Criteria.where("languages").is(entry.getLanguages()));
                        count = mongoOperations.count(check, Entry.class, "entries");
                    }

                    //If language combination doesn't exist throw exception.
                    if (count <= 0) throw new Exception("language combination does not exist.");

                    //save entry
                    mongoOperations.save(entry, "entries");
                    dbEntry = entry;
                }

                newList.add(new ObjectId(dbEntry.getId()));
            }

            WordList wl;

            //if no name is specified use default.
            if (name.equals(null)) {
                wl = new WordList(newList, languages);
            }
            else {
                wl = new WordList(name, newList, languages);
            }

            //save wordList in entries.
            mongoOperations.save(wl, "entries");

            //save changes to user
            user.addToWordLists(new ObjectId(wl.getId()));
            mongoOperations.save(user, "users");

            result.setSucces(true);

            HashMap<String, String> data = new HashMap<>();

            data.put("saved", "true");

            result.setData(data);
        }
        catch (Exception e) {
            result.setMsg(e.getMessage().toString());
            result.setSucces(false);
        }
    }

    /**
     * Returns wrapper with confirmation.
     * @return Wrapper
     */
    public Wrapper getConfirmation() {
        return result;
    }
}
