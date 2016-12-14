package App.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 14/12/2016.
 */
public class GroupInteraction {

    private MongoOperations mongoOperations = Tools.getMongoOperations();
    private User user;

    public GroupInteraction(String username) {
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(username));
        user = mongoOperations.findOne(getUser, User.class, "users");
    }

    public Wrapper CreateGroup(String input) {
        Wrapper out = new Wrapper();

        try {
            Tools.TeacherCheck(user);

            //Map to hashmap
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> groupMap = mapper.readValue(input, HashMap.class);

            //create new group
            Group group = new Group((String)groupMap.get("name"), new ObjectId(user.getId()));

            //save group
            mongoOperations.save(group, "users");

            out.setData("saved");
            out.setSucces(true);

        } catch (Exception e) {
            out.setSucces(false);
            out.setData(e.toString());
        }

        return out;
    }

    public Wrapper PublishTest(String testId, String groupId) {
        Wrapper out = new Wrapper();

        try {
            Tools.TeacherCheck(user);

            WordList test = mongoOperations.findById(testId, WordList.class, "entries");
            Group group = mongoOperations.findById(groupId, Group.class, "users");

            if (!group.getTeacher().equals(user.getId())) throw new Exception("You are not the teacher of this group.");

            group.addTest(new ObjectId(test.getId()));
            mongoOperations.save(group, "users");

            for (String studentId : group.getStudents()) {
                User student = mongoOperations.findById(new ObjectId(studentId), User.class, "users");
                student.addToWordLists(new ObjectId(test.getId()));
                mongoOperations.save(student);
            }

            out.setData("Published");
            out.setSucces(true);

        } catch (Exception e) {
            out.setSucces(false);
            out.setMsg(e.toString());
        }

        return out;
    }

    public Wrapper TestResults(String testId, String groupId) {
        Wrapper out = new Wrapper();

        try {
            Tools.TeacherCheck(user);

            WordList test = mongoOperations.findById(testId, WordList.class, "entries");
            Group group = mongoOperations.findById(groupId, Group.class, "users");

            if (!group.getTeacher().equals(user.getId())) throw new Exception("You are not the teacher of this group.");
            if (!group.getTests().contains(test.getId())) throw new Exception("Test is not published in group.");

            List<HashMap<String, Object>> data = new ArrayList<>();

            for (String studentId : group.getStudents()) {
                User student = mongoOperations.findById(new ObjectId(studentId), User.class, "users");
                List<Result> testResults = new ArrayList<>();

                for (Result result : student.getResults()) {
                    if (result.getList().equals(test.getId())) testResults.add(result);
                }

                HashMap<String, Object> userData = new HashMap<>();
                userData.put("student", student.getUsername());
                userData.put("results", testResults);

                data.add(userData);
            }
            out.setData(data);
            out.setSucces(true);

        } catch (Exception e) {
            out.setSucces(false);
            out.setMsg(e.toString());
        }

        return out;
    }

    public Wrapper AddStudents(String students, String groupId) {
        Wrapper out = new Wrapper();

        try {
            Tools.TeacherCheck(user);

            Group group = mongoOperations.findById(groupId, Group.class, "users");

            if (!group.getTeacher().equals(user.getId())) throw new Exception("You are not the teacher of this group.");

            //To array
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<String> studentList = mapper.readValue(students, ArrayList.class);

            List<ObjectId> studentIds = Tools.stringRangeToObjectIdRange(studentList);

            for (ObjectId studentId : studentIds) group.addStudent(studentId);

            mongoOperations.save(group);

            out.setData("students added");
            out.setSucces(true);

        } catch (Exception e) {
            out.setSucces(false);
            out.setMsg(e.toString());
        }
        return out;
    }
}
