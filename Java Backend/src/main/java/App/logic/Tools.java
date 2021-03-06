package App.logic;

import App.configuration.MongoConfig;
import model.Entry;
import model.User;
import model.WordList;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
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
     * Checks if user is teacher.
     * @param user given User object
     * @throws Exception when the person is not a user an exception is thrown
     */
    public static void teacherCheck(User user) throws Exception {
        if (!user.isTeacher()) throw new Exception("You are not permitted.");
    }

    /**
     * Reverses an entry.
     * @param entry Entry object, to be reversed.
     * @return reversed version of Entry object.
     */
    public static Entry reverseEntry(Entry entry) {
        return new Entry(entry.getTranslation(), entry.getWord(), new String[] {entry.getLanguages()[1], entry.getLanguages()[0]});
    }

    /**
     * Check if the word exists in the database.
     * @param entry user's entry
     * @return database's entry
     */
    public static Entry checkIfInDB(Entry entry, boolean full) {
        Query getWord = new Query();

        if (full) {
            getWord.addCriteria(Criteria.where("word").is(entry.getWord()).and("languages").is(entry.getLanguages()).and("translation").is(entry.getTranslation()));
        }
        else {
            getWord.addCriteria(Criteria.where("word").is(entry.getWord()).and("languages").is(entry.getLanguages()));
            long check = getMongoOperations().count(getWord, Entry.class, "entries");

            if (check <= 0) getWord = new Query(Criteria.where("translation").is(entry.getTranslation()).and("languages").is(entry.getLanguages()));
        }

        Entry test = getMongoOperations().findOne(getWord, Entry.class, "entries");
        return getMongoOperations().findOne(getWord, Entry.class, "entries");
    }

    /**
     * Converts a list of strings to a list of objectids.
     * @param input list of strings
     * @return list of objectids
     * @throws Exception conversion error
     */
    public static List<ObjectId> stringRangeToObjectIdRange(List<String> input) throws Exception {
        ArrayList<ObjectId> ids = new ArrayList<ObjectId>();

        for (String in : input) ids.add(new ObjectId(in));

        return ids;
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