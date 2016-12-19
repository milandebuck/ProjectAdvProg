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
@ContextConfiguration(classes = {App.configuration.MongoConfig.class, App.configuration.WebConfig.class, App.Application.class, App.logic.Tools.class})
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
    public void testOpenTests() {
        setup();
        List<WordList> wordLists = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            WordList wordList = new WordList("" + i, new ArrayList<ObjectId>(), new String[]{"English", "Dutch"});
            mongoOperations.save(wordList, "entries");
            user.addToWordLists(new ObjectId(wordList.getId()));
            mongoOperations.save(user, "users");
            wordLists.add(wordList);
        }

        Result testResult = new Result(0, 0, new ObjectId(wordLists.get(2).getId()), new String[]{"English", "Dutch"});
        user.addResult(testResult);
        mongoOperations.save(user, "users");

        Wrapper<List<HashMap<String,String>>> result = new GetObject(user.getUsername()).openTests();

        //Check if successful
        Assert.assertTrue(result.getSucces());

        //Get answers
        List<HashMap<String, String>> responseTests = result.getData();

        //check if all tests are in there.
        for (HashMap<String,String> responseTest : responseTests) {
            List<String> names = new ArrayList<String>(Arrays.asList("0", "1", "3", "4"));
            Assert.assertTrue(names.contains(responseTest.get("name")));
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
}
