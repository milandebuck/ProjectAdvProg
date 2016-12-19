package App.logic;

import model.Group;
import model.User;
import model.Wrapper;
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
import java.util.HashMap;

/**
 * Created by Robbe De Geyndt on 19/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.configuration.MongoConfig.class, App.configuration.WebConfig.class, App.Application.class, App.logic.Tools.class, App.logic.Converter.class})
@WebAppConfiguration
public class GroupInteractionTest {
    private MongoOperations mongoOperations;
    private User user;

    private void setup() {
        mongoOperations = Tools.getMongoOperations();
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is("UnitTestUser"));
        user = mongoOperations.findOne(getUser, User.class, "users");
    }

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
}
