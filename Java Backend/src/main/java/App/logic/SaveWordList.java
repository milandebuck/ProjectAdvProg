package App.logic;

import config.MongoConfig;
import model.Entry;
import model.User;
import model.WordList;
import model.Wrapper;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class SaveWordList {

    private Wrapper result;
    private List<Entry> entries;

    public SaveWordList(String username, String object) {

        result = new Wrapper();

        //Configuration DB connection.
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoOperations mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");

        try {
            entries = JsonToList.convert(object);
            List<ObjectId> newList = new ArrayList<ObjectId>();

            Query checkUser = new Query();
            checkUser.addCriteria(Criteria.where("username").is(username));
            User user = mongoOperations.findOne(checkUser, User.class, "users");

            for (Entry entry : entries) {

                Query checkEntry = new Query();
                checkEntry.addCriteria(Criteria.where("word").is(entry.getWord()).and("translation").is(entry.getTranslation()).and("languages").is(entry.getLanguages()));

                if (mongoOperations.count(checkEntry, "entries") == 0) {
                    if (user.isTeacher()) {
                        mongoOperations.save(entry, "entries");
                    }
                    else {
                        throw new Exception("This user is not allowed to perform this action.");
                    }
                }

                Entry dbEntry = mongoOperations.findOne(checkEntry, Entry.class, "entries");
                newList.add(dbEntry.getId());
            }
            String date = new SimpleDateFormat("EEEE d MMMM yyyy - HH:mm").format(new Date());
            WordList wl = new WordList(date, newList);

            user.addToWordLists(wl);

            mongoOperations.findAndRemove(checkUser, User.class, "users");
            mongoOperations.insert(user, "users");
            result.setSucces(true);

            HashMap<String, String> data = new HashMap<>();

            data.put("saved", "true");

            result.setData(data);
        }
        catch (Exception e) {
            result.setMsg(e.getMessage().toString());
            result.setSucces(false);
        }
    }

    public Wrapper getConfirmation() {
        return result;
    }
}
