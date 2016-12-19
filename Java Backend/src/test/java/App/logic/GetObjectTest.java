package App.logic;

import junit.framework.TestCase;
import model.*;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 19/12/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.configuration.MongoConfig.class, App.configuration.WebConfig.class, App.Application.class, App.logic.Tools.class, App.logic.Converter.class})
@WebAppConfiguration
public class GetObjectTest extends TestCase {
    private MongoOperations mongoOperations;
    private User user;

    private void setup() {
        mongoOperations = Tools.getMongoOperations();
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is("UnitTestUser"));
        user = mongoOperations.findOne(getUser, User.class, "users");
    }

    @Test
    public void testGetGroups() {
        setup();
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Group group = new Group(i +"", new ObjectId(user.getId()));
            mongoOperations.save(group, "users");
            groups.add(group);
        }

        Wrapper<List<HashMap<String, String>>> result = new GetObject(user.getUsername()).getGroups();

        Assert.assertTrue(result.getSucces());

        List<HashMap<String, String>> responseGroups = result.getData();
        
        //Check if groups are with user
        for (HashMap<String, String> responsegroup : responseGroups) {
            List<String> names = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4"));
            Assert.assertTrue(names.contains(responsegroup.get("name")));
        }

        //save locally unchanged user.
        mongoOperations.save(user);

        //delete created groups.
        for (Group group : groups) {
            mongoOperations.remove(group, "users");
        }
    }

    @Test
    public void testOpenTestsAndListNames() {
        setup();
        List<WordList> wordLists = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            WordList wordList = new WordList("" + i, new ArrayList<ObjectId>(), new String[]{"English", "Dutch"});
            mongoOperations.save(wordList, "entries");
            user.addToWordLists(new ObjectId(wordList.getId()));
            mongoOperations.save(user, "users");
            wordLists.add(wordList);
        }

        //Make one test solved.
        Result testResult = new Result(0, 0, new ObjectId(wordLists.get(2).getId()), new String[]{"English", "Dutch"});
        user.addResult(testResult);
        mongoOperations.save(user, "users");

        //Get open tests
        Wrapper<List<HashMap<String, String>>> resultOpen = new GetObject(user.getUsername()).openTests();
        //Get all tests
        Wrapper<List<HashMap<String, String>>> resultAll = new GetObject(user.getUsername()).listNames();

        //Check if successful
        Assert.assertTrue(resultOpen.getSucces());
        Assert.assertTrue(resultAll.getSucces());

        //Get answers
        List<HashMap<String, String>> responseOpenTests = resultOpen.getData();
        List<HashMap<String, String>> responseAllTests = resultAll.getData();

        //check if all tests are in there.
        Assert.assertTrue(responseOpenTests.size() == 4);
        Assert.assertTrue(responseAllTests.size() == 5);
        for (HashMap<String,String> responseTest : responseOpenTests) {
            List<String> namesOpen = new ArrayList<String>(Arrays.asList("0", "1", "3", "4"));
            Assert.assertTrue(namesOpen.contains(responseTest.get("name")));
        }
        for (HashMap<String,String> responseTest: responseAllTests) {
            List<String> namesAll = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4"));
            Assert.assertTrue(namesAll.contains(responseTest.get("name")));
        }

        //clean up db.
        for (WordList wordList : wordLists) {
            user.removeFromWordLists(new ObjectId(wordList.getId()));
            mongoOperations.remove(wordList, "entries");
        }

        user.removeResult(testResult);
        mongoOperations.save(user);
    }

    @Test
    public void testIsTeacher() {
        setup();

        //Should return false for teacher.
        user.setTeacher(false);
        mongoOperations.save(user);

        Wrapper<HashMap<String, Boolean>> result = new GetObject(user.getUsername()).isTeacher();
        HashMap<String, Boolean> noteacher = result.getData();
        Assert.assertTrue(noteacher.get("teacher") == false);

        //Should return true for teacher.
        user.setTeacher(true);
        mongoOperations.save(user);

        Wrapper<HashMap<String, Boolean>> result2 = new GetObject(user.getUsername()).isTeacher();
        HashMap<String, Boolean> teacher = result2.getData();
        Assert.assertTrue(teacher.get("teacher") == true);
    }

    @Test
    public void testGetList() {
        setup();

        List<Entry> dbEntries = mongoOperations.find(new Query().limit(5), Entry.class, "entries");
        List<ObjectId> dbEntriesId = new ArrayList<>();
        //get the ids
        for (Entry entry : dbEntries) dbEntriesId.add(new ObjectId(entry.getId()));

        WordList wordList = new WordList("testList", dbEntriesId,new String[] {"English", "Dutch"});

        mongoOperations.save(wordList, "entries");

        //add list to user.
        user.addToWordLists(new ObjectId(wordList.getId()));
        mongoOperations.save(user, "users");

        Wrapper<HashMap<String, Object>> result = new GetObject(user.getUsername()).getList(wordList.getId());

        //testing
        Assert.assertTrue(result.getSucces());
        Assert.assertTrue(result.getData().get("name").equals("testList"));

        List<Entry> entries = new ArrayList<>();
        try {
            entries = (ArrayList<Entry>)result.getData().get("list");
        } catch (Exception e) {
            fail("Json to array failed.");
        }

        //check if size is the same
        assertTrue(entries.size() == dbEntries.size());

        //check if IDs match
        for (Entry entry : entries) assertTrue(dbEntriesId.contains(new ObjectId(entry.getId())));

        //clean db
        user.removeFromWordLists(new ObjectId(wordList.getId()));
        mongoOperations.save(user, "users");
        mongoOperations.remove(wordList, "entries");
    }

    @Test
    public void testGetUser() {
        setup();

        //queries that will be tested
        List<String> namesToTest = new ArrayList<>(Arrays.asList("unittestuser", "unit", "test", "user"));

        for (String name : namesToTest) {
            Wrapper<List<HashMap<String,String>>> result = new GetObject(user.getUsername()).getUser(name);
            List<HashMap<String,String>> searchResults = result.getData();
            boolean found = false;

            //Check if user is found.
            for (HashMap<String,String> searchResult : searchResults) {
                if (searchResult.get("username").equals(user.getUsername())) found = true;
            }

            Assert.assertTrue(found);
        }

    }
}
