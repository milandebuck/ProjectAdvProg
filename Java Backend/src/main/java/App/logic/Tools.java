package App.logic;

import App.configuration.MongoConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Entry;
import model.WordList;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class with tools used by several other classes.
 * Created by Robbe De Geyndt on 3/12/2016.
 */
public class Tools {

    /**
     * Creates MongoOperations object for database transactions.
     * @return MongoOperations
     */
    public static MongoOperations getMongoOperations() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
        return (MongoOperations)ctx.getBean("mongoTemplate");
    }

    /**
     * Converts JSON String to a list of entries.
     * @param json user's request
     * @return list of entries
     * @throws Exception when parsing fails
     */
    public static List<Entry> jsonToArrayList(String json) throws Exception {

        List<Object> entries = new ArrayList<Object>();
        List<Entry> out = new ArrayList<Entry>();

        //Get input
        ObjectMapper mapper = new ObjectMapper();
        entries = mapper.readValue(json, ArrayList.class);

        //Add entries in list
        for (Object value : entries) {
            Entry entry = mapper.readValue(new JSONObject((HashMap<String,String>)value).toString(), Entry.class);
            out.add(entry);
        }

        //return result
        return out;
    }

    /**
     * Checks if given list of Entries already exists in wordlists of user.
     * @param lists wordlists of user
     * @param exercise new list of entries
     * @return ObjectId of list or null
     */
    public static ObjectId exerciseIsList(List<String> lists, List<Entry> exercise) {
        MongoOperations mongoOperations = getMongoOperations();

        //Go over every list.
        for (String stringList : lists) {
            ObjectId list = new ObjectId(stringList);
            List<String> entryIds = mongoOperations.findById(list, WordList.class, "entries").getEntryList();

            //if lists have the same size
            if (entryIds.size() == exercise.size()) {

                //count how many entries match
                int correct = 0;
                for (int i = 0; i < entryIds.size(); i++) {
                    if (entryIds.contains(exercise.get(i).getId())) {
                        correct += 1;
                    }
                }

                //check if all entries match.
                if (correct == exercise.size()) {
                    return list;
                }
            }
        }
        //if nothing is found return null.
        return null;
    }
}