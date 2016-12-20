package App.logic;

import model.*;
import org.bson.types.ObjectId;
import org.json.JSONObject;
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
 * Tests for GroupInteraction class.
 * Created by Robbe De Geyndt on 19/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.configuration.MongoConfig.class, App.configuration.WebConfig.class, App.Application.class, App.logic.Tools.class, App.logic.Converter.class})
@WebAppConfiguration
public class GroupInteractionTest {
    private MongoOperations mongoOperations;
    private User user;
    private User user2;

    private void setup() {
        mongoOperations = Tools.getMongoOperations();
        Query getUser1 = new Query(Criteria.where("username").is("UnitTestUser"));
        user = mongoOperations.findOne(getUser1, User.class, "users");
        Query getUser2 = new Query(Criteria.where("username").is("UnitTestUser2"));
        user2 = mongoOperations.findOne(getUser2, User.class, "users");
    }

    /**
     * Test if group can be created correctly.
     */
    @Test
    public void testCreateGroup() {
        setup();

        //Make group
        HashMap in = new HashMap();
        in.put("name", "UnitTest");
        Wrapper<HashMap> response = new GroupInteraction(user.getUsername()).createGroup(JSONObject.valueToString(in));
        //Check success
        Assert.assertTrue(response.getSucces());

        //Search group in database
        Query getGroup = new Query(Criteria.where("name").is("UnitTest"));
        Group group = mongoOperations.findOne(getGroup, Group.class, "users");
        //Check if exists
        Assert.assertTrue(group != null);
        //Check if teacher is correct
        Assert.assertTrue(group.getTeacher().equals(user.getId()));

        //update user
        setup();

        //check if groupid is added to user
        Assert.assertTrue(user.getGroups().contains(new ObjectId(group.getId())));

        //reset DB
        user.setGroups(new ArrayList<ObjectId>());
        mongoOperations.save(user, "users");
        mongoOperations.remove(group, "users");
    }

    /**
     * Tests if all interactions with a group can be completed successfully.
     */
    @Test
    public void testGroupInteractions() {
        setup();

        //Make group
        HashMap in = new HashMap();
        in.put("name", "UnitTest2");
        new GroupInteraction(user.getUsername()).createGroup(JSONObject.valueToString(in));
        Group group = mongoOperations.findOne(new Query(Criteria.where("name").is("UnitTest2")), Group.class, "users");

        String test = JSONObject.valueToString(new ArrayList<String>(Arrays.asList(user2.getId())));
        //--Add users to group--
        Wrapper addStudentsResponse = new GroupInteraction(user.getUsername()).addStudents(JSONObject.valueToString(new ArrayList<String>(Arrays.asList(user2.getId()))), group.getId());
        //Check success
        Assert.assertTrue(addStudentsResponse.getSucces());

        //redo setup
        setup();

        //Group in user1?
        Assert.assertTrue(user.getGroups().get(0).equals(new ObjectId(group.getId())));
        //Group in user2?
        Assert.assertTrue(user2.getGroups().get(0).equals(new ObjectId(group.getId())));

        //--Try publishing test--
        //Setup wordList
        List<ObjectId> objectIds = new ArrayList<>();
        for (int i = 0; i < 5; i++) objectIds.add(new ObjectId());
        WordList wordList = new WordList("UnitTestList", objectIds, new String[] {"English", "Dutch"});
        mongoOperations.save(wordList, "entries");

        //publish Test
        Wrapper publishResponse = new GroupInteraction(user.getUsername()).publishTest(wordList.getId(), group.getId());
        //check success
        Assert.assertTrue(publishResponse.getSucces());

        //redo setup
        setup();

        //Test in user2?
        Assert.assertTrue(user2.getWordLists().get(0).equals(wordList.getId()));

        //--Try getting test back --
        Wrapper gettestResponse = new GroupInteraction(user.getUsername()).getTests(group.getId());

        String wordListName = (String)((ArrayList<HashMap<String, String>>)gettestResponse.getData()).get(0).get("name");
        String wordlistId = (String)((ArrayList<HashMap<String, String>>)gettestResponse.getData()).get(0).get("id");

        //check on id
        Assert.assertEquals(wordList.getId(), wordlistId);
        //check on name
        Assert.assertEquals(wordList.getName(), wordListName);

        //--Try getting list of students--
        Wrapper getStudentsResponse = new GroupInteraction(user.getUsername()).getStudents(group.getId());
        //check success
        Assert.assertTrue(getStudentsResponse.getSucces());

        //get name and id of student
        String name = (String)((ArrayList<HashMap>)getStudentsResponse.getData()).get(0).get("name");
        String id = (String)((ArrayList<HashMap>)getStudentsResponse.getData()).get(0).get("id");

        //Check if length is correct
        Assert.assertTrue(((ArrayList<HashMap>)getStudentsResponse.getData()).size() == 1);

        //check if matching
        Assert.assertEquals(name, user2.getUsername());
        Assert.assertEquals(id, user2.getId());

        //--Try getting test results from users--
        //Create Result
        Result result = new Result(3, 5, new ObjectId(wordList.getId()), new String[] {"English", "Dutch"});
        user2.addResult(result);
        mongoOperations.save(user2, "users");

        //Get user results
        Wrapper resultsResponse = new GroupInteraction(user.getUsername()).testResults(wordList.getId(), group.getId());
        //check success
        Assert.assertTrue(resultsResponse.getSucces());

        //check length
        Assert.assertTrue(((ArrayList<HashMap>)resultsResponse.getData()).size() == 1);

        //Get needed data
        String nameResult = (String)((ArrayList<HashMap>)resultsResponse.getData()).get(0).get("student");
        Result studentResult = (Result)((ArrayList<Result>)((ArrayList<HashMap>)resultsResponse.getData()).get(0).get("results")).get(0);

        //is user2?
        Assert.assertEquals(nameResult, user2.getUsername());

        //is result?
        Assert.assertEquals(result.getDate(), studentResult.getDate());
        Assert.assertTrue(result.getScore() == studentResult.getScore());
        Assert.assertTrue(result.getMax() == studentResult.getMax());
        Assert.assertEquals(result.getList(), studentResult.getList());

        //--Clean DB--
        user.setGroups(new ArrayList<ObjectId>());
        user.setResults(new ArrayList<Result>());
        user.setWordLists(new ArrayList<ObjectId>());
        user2.setGroups(new ArrayList<ObjectId>());
        user2.setResults(new ArrayList<Result>());
        user2.setWordLists(new ArrayList<ObjectId>());
        mongoOperations.save(user, "users");
        mongoOperations.save(user2, "users");
        mongoOperations.remove(group, "users");
        mongoOperations.remove(wordList, "entries");
    }
}
