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
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class SaveWordList {

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private Wrapper result;
    private List<Entry> entries;

    public SaveWordList(String username, String object) {

        result = new Wrapper();

        try {
            Wrapper input = new Wrapper();

            //Json to Wrapper
            ObjectMapper mapper = new ObjectMapper();
            input = mapper.readValue(object, Wrapper.class);

            //Check if valid
            if (!input.getSucces()) {
                throw new Exception("No valid input");
            }

            //Get get names
            String name = ((HashMap<String,String>)input.getData()).get("name");
            String inputMap = JSONObject.valueToString(((HashMap<String,String>)input.getData()).get("list"));

            entries = Tools.jsonToArrayList(inputMap);
            List<ObjectId> newList = new ArrayList<ObjectId>();

            Query checkUser = new Query();
            checkUser.addCriteria(Criteria.where("username").is(username));
            User user = mongoOperations.findOne(checkUser, User.class, "users");

            for (Entry entry : entries) {

                Query checkEntry = new Query();
                checkEntry.addCriteria(Criteria.where("word").is(entry.getWord()).and("translation").is(entry.getTranslation()).and("languages").is(entry.getLanguages()));

                if (mongoOperations.count(checkEntry, "entries") == 0) {
                    if (user.isTeacher()) {
                        mongoOperations.save(entry, "entries");
                    }
                    else {
                        throw new Exception("This user is not allowed to perform this action.");
                    }
                }

                Entry dbEntry = mongoOperations.findOne(checkEntry, Entry.class, "entries");
                newList.add(dbEntry.getId());
            }

            WordList wl;

            //if no name is specified use default.
            if (name.equals(null)) {
                wl = new WordList(newList);
            }
            else {
                wl = new WordList(name, newList);
            }

            //save wordlist in entries.
            mongoOperations.save(wl, "entries");

            //save changes to user
            user.addToWordLists(wl.getId());
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

    public Wrapper getConfirmation() {
        return result;
    }
}
