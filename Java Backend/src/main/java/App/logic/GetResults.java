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
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class for returning test results.
 * Created by Robbe De Geyndt on 10/12/2016.
 */
public class GetResults {

    //Configuration DB connection.
    private MongoOperations mongoOperations = Tools.getMongoOperations();


    private String username;
    private Wrapper out;

    /**
     * Gettining test results and sorting them by languages.
     * @param username name of user
     */
    public GetResults(String username) {

        this.username = username;
        this.out = new Wrapper();

        try {
            Query getUser = new Query();
            getUser.addCriteria(Criteria.where("username").is(username));

            User user = mongoOperations.findOne(getUser, User.class, "users");
            List<Result> userResults = user.getResults();

            List<JSONObject> response = new ArrayList<>();

            ConcurrentLinkedQueue<HashMap<String,Object>> data = new ConcurrentLinkedQueue<>();

            ConcurrentLinkedQueue<String[]> keys = new ConcurrentLinkedQueue<>();

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
                HashMap<String, Object> object = new HashMap<>();
                ArrayList<Result> value = new ArrayList<Result>();

                for (Result result : userResults) {
                    if ((result.getLanguages()[0].equals(key[0])) && (result.getLanguages()[1].equals(key[1])))
                        value.add(result);
                }
                object.put("translations", key[0] + " to " + key[1]);
                object.put("tests", value);
                data.add(object);
            }

            out.setData(data);
            out.setSucces(true);

        } catch (Exception e) {
            out.setSucces(false);
            out.setMsg(e.toString());
        }

    }

    /**
     * Returns wrapper with result.
     * @return list of test results by languages
     */
    public Wrapper userResults() { return out; }
}
