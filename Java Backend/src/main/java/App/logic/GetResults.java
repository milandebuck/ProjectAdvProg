package App.logic;

import model.Result;
import model.User;
import model.Wrapper;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 10/12/2016.
 */
public class GetResults {

    //Configuration DB connection.
    private MongoOperations mongoOperations = Tools.getMongoOperations();


    private String username;
    private Wrapper out;


    public GetResults(String username) {

        this.username = username;
        this.out = new Wrapper();

        try {
            Query getUser = new Query();
            getUser.addCriteria(Criteria.where("username").is(username));

            User user = mongoOperations.findOne(getUser, User.class, "users");
            List<Result> userResults = user.getResults();

            List<JSONObject> response = new ArrayList<>();

            HashMap<String, ArrayList<Result>> data = new HashMap<>();

            List<String[]> keys = new ArrayList<String[]>();

            //setup keys
            for (Result result : userResults) {
                String[] languages = result.getLanguages();

                if (keys.size() == 0) {
                    keys.add(languages);
                } else {
                    for (String[] key : keys) {
                        int i = 0;

                        if ((languages[0].equals(key[0])) && languages[1].equals(key[1])) i++;

                        if (i == 0) keys.add(languages);
                    }
                }
            }

            //insert by correct key
            for (String[] key : keys) {
                ArrayList<Result> value = new ArrayList<Result>();

                for (Result result : userResults) {
                    if ((result.getLanguages()[0].equals(key[0])) && (result.getLanguages()[1].equals(key[1])))
                        value.add(result);
                }

                data.put(key[0] + " to " + key[1], value);
            }

            out.setData(data);
            out.setSucces(true);

        } catch (Exception e) {
            out.setSucces(false);
            out.setMsg(e.getMessage());
        }

    }


    public Wrapper userResults() { return out; }
}
