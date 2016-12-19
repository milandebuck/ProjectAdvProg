package App.logic;

import junit.framework.TestCase;
import model.Group;
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
}
