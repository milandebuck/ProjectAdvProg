package App.logic;

import junit.framework.TestCase;
import model.Entry;
import model.User;
import model.WordList;
import model.Wrapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Test for saving lists/tests.
 * Created by Robbe De Geyndt on 4/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.configuration.MongoConfig.class, App.Application.class, App.logic.Tools.class})
public class SaveWordListTest extends TestCase{

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private List<Entry> dbList;
    private List<Entry> testList;
    private SaveWordList saveWordList;

    /**
     * generates list with geven name.
     * @param listname
     */
    private void setup(String listname) {
        dbList = new ArrayList<Entry>();
        testList = new ArrayList<Entry>();

        for (int i = 0; i < 5; i++) {
            Entry dbEntry = mongoOperations.findOne(new Query(), Entry.class, "entries");
            dbList.add(dbEntry);
            testList.add(new Entry(dbEntry.getWord(), dbEntry.getTranslation(), dbEntry.getLanguages()));
        }

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", listname);
        data.put("list", testList);

        Wrapper wrapper = new Wrapper();
        wrapper.setData(data);
        wrapper.setSucces(true);

        saveWordList = new SaveWordList("UnitTestUser", new JSONObject(wrapper).toString());
    }

    /**
     * Tests if lists are correctly saved.
     */
    @Test
    public void testSaveList() {
        setup("----TEST 1----");

        //Check if making list succeeded.
        assertTrue(saveWordList.getConfirmation().getSucces());

        //Check if list is made & correct.
        Query getList = new Query();
        getList.addCriteria(Criteria.where("name").is("----TEST 1----"));

        List<WordList> wordLists = mongoOperations.find(getList, WordList.class, "entries");

        assertTrue(wordLists.size() == 1);

        for (Entry entry : dbList) {
            assertTrue(wordLists.get(0).getEntryList().contains(entry.getId()));
        }

        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is("UnitTestUser"));
        User user = mongoOperations.findOne(getUser, User.class, "users");

        //remove list from user
        user.removeFromWordLists(wordLists.get(0).getId());
        mongoOperations.save(user, "users");

        //delete list
        mongoOperations.remove(getList, WordList.class, "entries");
    }

    @Test
    public void testUserList() {
        setup("----TEST 2----");

        //Check if making list succeeded.
        assertTrue(saveWordList.getConfirmation().getSucces());

        //check if list appears in user document.
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is("UnitTestUser"));

        User user = mongoOperations.findOne(getUser, User.class, "users");

        Query getList = new Query();
        getList.addCriteria(Criteria.where("name").is("----TEST 2----"));

        WordList list = mongoOperations.findOne(getList, WordList.class, "entries");

        //Does the list appear in the user object
        assertTrue(user.getWordLists().contains(list.getId()));

        //remove list from user
        user.removeFromWordLists(list.getId());
        mongoOperations.save(user, "users");

        //delete list
        mongoOperations.remove(getList, WordList.class, "entries");
    }
}
