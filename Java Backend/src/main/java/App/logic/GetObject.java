package App.logic;

import model.Entry;
import model.User;
import model.WordList;
import model.Wrapper;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 12/12/2016.
 */
public class GetObject {

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private User user;

    public GetObject(String username) {
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(username));
        user = mongoOperations.findOne(getUser, User.class, "users");
    }

    public Wrapper listNames() {
        Wrapper wrapper = new Wrapper();
        try {
            List<String> userListIds = user.getWordLists();
            List<HashMap<String, String>> listNames = new ArrayList<HashMap<String, String>>();

            for (String stringId : userListIds) {
                ObjectId id = new ObjectId(stringId);
                WordList wl = mongoOperations.findById(id, WordList.class, "entries");

                HashMap<String, String> object = new HashMap<>();
                object.put(wl.getName(), wl.getId());
                listNames.add(object);
            }

            wrapper.setData(listNames);
            wrapper.setSucces(true);
        } catch (Exception e) {
            wrapper.setSucces(false);
            wrapper.setMsg(e.toString());
        }

        return wrapper;
    }

    public Wrapper getList(String input) {
        Wrapper wrapper = new Wrapper();

        try {
            ObjectId listId = new ObjectId(input);
            WordList list = mongoOperations.findById(listId, WordList.class, "entries");

            List<Entry> entries = new ArrayList<>();

            for (String entryId : list.getEntryList()) {
                Entry entry = mongoOperations.findById(new ObjectId(entryId), Entry.class, "entries");
                entries.add(entry);
            }

            HashMap<String, Object> output = new HashMap<>();
            output.put("name", list.getName());
            output.put("list", entries);

            wrapper.setData(output);
            wrapper.setSucces(true);
        } catch (Exception e) {
            wrapper.setSucces(false);
            wrapper.setMsg(e.toString());
        }
        return wrapper;
    }
}
