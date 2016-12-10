package App.logic;

import model.User;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by Robbe De Geyndt on 10/12/2016.
 */
public class GetObject {

    //Configuration DB connection.
    private MongoOperations mongoOperations = Tools.getMongoOperations();

    private String username;

    public GetObject(String username) {
        this.username = username;
    }

    public String user() {
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(username));

        User user = mongoOperations.findOne(getUser, User.class, "users");

        return new JSONObject(user).toString();
    }
}
