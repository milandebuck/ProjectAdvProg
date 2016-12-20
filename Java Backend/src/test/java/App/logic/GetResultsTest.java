package App.logic;

import model.Result;
import model.User;
import model.Wrapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Tests for GetResults class.
 * Created by Robbe De Geyndt on 19/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.configuration.MongoConfig.class, App.configuration.WebConfig.class, App.Application.class, App.logic.Tools.class, App.logic.Converter.class})
@WebAppConfiguration
public class GetResultsTest {
    private MongoOperations mongoOperations;
    private User user;
    private List<Result> results;

    /**
     * Setup for test, creating lists.
     */
    private void setup() {
        mongoOperations = Tools.getMongoOperations();
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is("UnitTestUser"));
        user = mongoOperations.findOne(getUser, User.class, "users");

        results = new ArrayList<>();

        Result result1 = new Result(1, 5, new ObjectId(), new String[]{"English", "Dutch"});
        results.add(result1);
        Result result2 = new Result(2, 5, new ObjectId(), new String[]{"English", "Dutch"});
        results.add(result2);
        Result result3 = new Result(3, 5, new ObjectId(), new String[]{"English", "Dutch"});
        results.add(result3);
        Result result4 = new Result(4, 5, new ObjectId(), new String[]{"Dutch", "English"});
        results.add(result4);
        Result result5 = new Result(5, 5, new ObjectId(), new String[]{"Dutch", "English"});
        results.add(result5);
    }

    /**
     * Cleaning up the database after testing.
     */
    private void cleanDB() {
        user.setResults(new ArrayList<Result>());
        mongoOperations.save(user,"users");
    }

    /**
     * Tests if user scores are returned correctly filtered in languages.
     */
    @Test
    public void testUserResults() {

        //setup
        setup();

        user.setResults(results);
        mongoOperations.save(user, "users");

        Wrapper<ConcurrentLinkedQueue<HashMap>> response = new GetResults(user.getUsername()).userResults();

        //Test if response succeeded
        Assert.assertTrue(response.getSucces());

        ConcurrentLinkedQueue<HashMap> maps = response.getData();

        //List should have size of 2.
        Assert.assertTrue(maps.size() == 2);

        List<String> translations = new ArrayList<>();
        List<Result> tests = new ArrayList<>();

        //get info.
        for (HashMap map : maps) {
            translations.add((String)map.get("translations"));
            tests.addAll((ArrayList<Result>)map.get("tests"));
        }

        //Check if there are 5 records.
        Assert.assertTrue(tests.size() == 5);

        //create list of unique strings for original results and response.
        List<String> origUniqueStrings = new ArrayList<>();
        List<String> respUniqueStrings = new ArrayList<>();
        for (Result result : results) origUniqueStrings.add(result.getScore() + "" + result.getMax() + result.getList() + result.getDate() + result.getLanguages()[0] + result.getLanguages()[1]);
        for (Result result : tests) respUniqueStrings.add(result.getScore() + "" + result.getMax() + result.getList() + result.getDate() + result.getLanguages()[0] + result.getLanguages()[1]);

        //Check if all results have been given back.
        for (String x : origUniqueStrings) Assert.assertTrue(respUniqueStrings.contains(x));

        //Check if the different captions are made correctly.
        Assert.assertTrue(translations.contains("English to Dutch"));
        Assert.assertTrue(translations.contains("Dutch to English"));

        //clean db
        cleanDB();
    }
}
