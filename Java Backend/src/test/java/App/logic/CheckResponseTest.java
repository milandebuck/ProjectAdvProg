package App.logic;

import junit.framework.TestCase;
import model.*;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test for word checker.
 * Created by Robbe De Geyndt on 26/11/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.configuration.MongoConfig.class, App.configuration.WebConfig.class, App.Application.class, App.logic.Tools.class})
@WebAppConfiguration
public class CheckResponseTest extends TestCase {

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private List<Entry> entries1 = new ArrayList<Entry>();

    /**
     * Puts testdata in Array for testing.
     */
    private void setup() {
        //Correct entries
        entries1.add(new Entry("(aero)plane", "het vliegtuig", new String[]{"English", "Dutch"}));
        entries1.add(new Entry("(arch)angel", "de (aarts)engel", new String[]{"English", "Dutch"}));

        //Faulty entries
        entries1.add(new Entry("(blood) circulation", "wrong answer", new String[]{"English", "Dutch"}));
        entries1.add(new Entry("(bride)groom", "wrong answer", new String[]{"English", "Dutch"}));


    }

    /**
     * Removes testdata from database.
     */
    private void cleanDB() {
        //Get user
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is("UnitTestUser"));
        User user = mongoOperations.findOne(getUser, User.class, "users");

        //Clean database
        List<ObjectId> list = user.getWordLists();
        List<Result> results = user.getResults();
        int lastList = list.size() - 1;
        int lastResult = results.size() - 1;

        WordList wl = mongoOperations.findById(list.get(lastList), WordList.class, "entries");
        mongoOperations.remove(wl);

        user.removeFromWordLists(list.get(lastList));
        user.removeResult(results.get(lastResult));
        mongoOperations.save(user, "users");
    }

    /**
     * Check if API rejects faulty input.
     */
    /*@Test
    public void testRejectFaultyInput() {
        setup();

        wrapper1.setData(entries1);
        wrapper1.setSucces(false);

        CheckResponse cr1 = new CheckResponse("UnitTestUser", new JSONObject(wrapper1).toString());

        assertEquals("No valid input", cr1.getResult().getMsg());
    }*/

    /**
     * Test if score was correctly calculated.
     */
    @Test
    public void testCalculateScore() {
        setup();

        CheckResponse cr2 = new CheckResponse("UnitTestUser", JSONObject.valueToString(entries1));

        Map<String,String> obj = (HashMap<String,String>)cr2.getResult().getData();

        assertEquals(2, obj.get("score"));
        assertEquals(4, obj.get("max"));

        cleanDB();
    }

    /**
     * Test if the corrections are returned.
     */
    @Test
    public void testFeedback() {
        setup();

        CheckResponse cr3 = new CheckResponse("UnitTestUser", new JSONObject(entries1).toString());

        Map obj = (HashMap)cr3.getResult().getData();

        List<Entry> faulty = (List<Entry>)obj.get("faulty");

        assertEquals(2, faulty.size());
        assertEquals("(blood) circulation", faulty.get(0).getWord());
        assertEquals("(bride)groom", faulty.get(1).getWord());

        cleanDB();
    }

    /**
     * Test if everything is in the database.
     */
    @Test
    public void testDB() {
        setup();

        CheckResponse cr4 = new CheckResponse("UnitTestUser", new JSONObject(entries1).toString());

        //Get user
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is("UnitTestUser"));
        User user = mongoOperations.findOne(getUser, User.class, "users");

        //Get results
        List<Result> results = user.getResults();
        Result lastResult = results.get(results.size() - 1);

        //Get wordlists
        List<ObjectId> wordListIds = user.getWordLists();
        ObjectId lastListId = wordListIds.get(wordListIds.size() - 1);

        //Check all values
        assertEquals(lastResult.getMax(), 4);
        assertEquals(lastResult.getScore(), 2);
        assertEquals(lastResult.getList(), lastListId);

        WordList wl = mongoOperations.findById(lastListId, WordList.class, "entries");

        assertTrue(wl.getEntryList().size() == 4);

        cleanDB();
    }
}