package App.logic;

import model.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class used for getting access to database.
 * Created by Robbe De Geyndt on 12/12/2016.
 */
public class GetObject {

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private User user;

    /**
     * Constructor.
     * @param username username of logged in user
     */
    public GetObject(String username) {
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(username));
        user = mongoOperations.findOne(getUser, User.class, "users");
    }

    /**
     * Gives back groups that logged in user is part of.
     * @return list with the names of the groups and their IDs
     */
    public  Wrapper getGroups() {
        Wrapper<List<HashMap<String, String>>> wrapper = new Wrapper();
        try {
            List<HashMap<String, String>> out = new ArrayList<>();
            List<ObjectId> groupIds = user.getGroups();

            if (groupIds.size() != 0) {

                for (ObjectId groupId : groupIds) {
                    HashMap<String, String> group = new HashMap<>();
                    String name = mongoOperations.findById(groupId, Group.class, "users").getName();

                    group.put("name", name);
                    group.put("id", groupId.toHexString());

                    out.add(group);
                }
            }
            wrapper.setData(out);
            wrapper.setSucces(true);
        } catch (Exception e) {
            wrapper.setSucces(false);
            wrapper.setMsg(e.toString());
        }
        return wrapper;
    }

    /**
     * Gives back list of tests that have no result objects attached to them.
     * @return list of test names and their IDs
     */
    public Wrapper openTests() {
        Wrapper<List<HashMap<String,String>>> wrapper = new Wrapper();
        try {
            List<String> tests = user.getWordLists();
            List<Result> results = user.getResults();
            List<String> resultsTests = new ArrayList<>();
            List<String> unsolvedTests = new ArrayList<>();
            List<HashMap<String, String>> out = new ArrayList<>();

            for (Result result : results) resultsTests.add(result.getList());

            for (String test : tests) if (!resultsTests.contains(test)) unsolvedTests.add(test);

            for (String unsolvedTest : unsolvedTests) {
                String name = mongoOperations.findById(unsolvedTest, WordList.class, "entries").getName();
                HashMap<String, String> test = new HashMap<>();
                test.put("name", name);
                test.put("id", unsolvedTest);
                out.add(test);
            }

            wrapper.setData(out);
            wrapper.setSucces(true);
        } catch (Exception e) {
            wrapper.setSucces(false);
            wrapper.setMsg(e.toString());
        }
        return wrapper;
    }

    /**
     * Returns boolean true when the logged in user is a teacher.
     * @return answer with boolean
     */
    public Wrapper isTeacher() {
        Wrapper<HashMap<String, Boolean>> wrapper = new Wrapper();
        HashMap<String, Boolean> out = new HashMap<>();
        try {
            out.put("teacher", user.isTeacher());
            wrapper.setData(out);
            wrapper.setSucces(true);
        } catch (Exception e) {
            wrapper.setSucces(false);
            wrapper.setMsg(e.toString());
        }
        return wrapper;
    }

    /**
     * Returns list of tests from user.
     * @return list of names of tests an their IDs
     */
    public Wrapper listNames() {
        Wrapper<List<HashMap<String, String>>> wrapper = new Wrapper();
        try {
            List<String> userListIds = user.getWordLists();
            List<HashMap<String, String>> listNames = new ArrayList<HashMap<String, String>>();

            for (String stringId : userListIds) {
                ObjectId id = new ObjectId(stringId);
                WordList wl = mongoOperations.findById(id, WordList.class, "entries");

                HashMap<String, String> object = new HashMap<>();
                object.put("name", wl.getName());
                object.put("id", wl.getId());
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

    /**
     * Returns test based of ID.
     * @param input id of test
     * @return name of test and the entries
     */
    public Wrapper getList(String input) {
        Wrapper wrapper = new Wrapper();
        boolean reversed = false;

        try {
            ObjectId listId = new ObjectId(input);
            WordList list = mongoOperations.findById(listId, WordList.class, "entries");

            String[] entryLanguages = mongoOperations.findById(list.getEntryList().get(0), Entry.class, "entries").getLanguages();

            if ((entryLanguages[0].equals(list.getLanguages()[1])) && (entryLanguages[1].equals(list.getLanguages()[0]))) reversed = true;

            List<Entry> entries = new ArrayList<>();

            for (String entryId : list.getEntryList()) {
                Entry entry = mongoOperations.findById(new ObjectId(entryId), Entry.class, "entries");

                if (reversed) entry = Tools.reverseEntry(entry);

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

    /**
     * Searches users based on string.
     * @param name string that is used for searching
     * @return list of usernames that contain the specified String, and the userIDs
     */
    public Wrapper getUser(String name) {
        Wrapper<List<HashMap<String,String>>> wrapper = new Wrapper();

        try {
            Query searchUser = new Query();
            searchUser.addCriteria(Criteria.where("username").regex(name, "i"));

            List<User> result = mongoOperations.find(searchUser, User.class, "users");

            List<HashMap<String, String>> out = new ArrayList<>();

            for ( User user : result) {
                HashMap<String, String> userData = new HashMap<>();
                userData.put("username", user.getUsername());
                userData.put("id", user.getId());

                out.add(userData);
            }

            wrapper.setData(out);
            wrapper.setSucces(true);

        } catch (Exception e) {
            wrapper.setSucces(false);
            wrapper.setMsg(e.toString());
        }

        return wrapper;
    }
}
