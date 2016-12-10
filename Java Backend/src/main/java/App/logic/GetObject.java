package App.logic;

import model.Result;
import model.User;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

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

    public String userResults() {
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(username));

        User user = mongoOperations.findOne(getUser, User.class, "users");
        List<Result> userResults = user.getResults();

        List<JSONObject> response = new ArrayList<>();

        for (Result result : userResults) {
            response.add(new JSONObject(result));
        }

        return response.toString();
    }
}
