package model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robbe De Geyndt on 12/12/2016.
 */
@Document(collection = "users")
public class Group {
    @Id
    @JsonTypeId
    private ObjectId id;
    private String name;
    private ObjectId teacher;
    private List<ObjectId> students;
    private List<ObjectId> tests;

    public Group() {

    }

    public Group(String name, ObjectId teacher, List<ObjectId> students, List<ObjectId> tests) {
        this.name = name;
        this.teacher = teacher;
        this.students = students;
        this.tests = tests;
    }

    public Group(String name, ObjectId teacher) {
        this.name = name;
        this.teacher = teacher;
        this.students = new ArrayList<>();
        this.tests = new ArrayList<>();
    }

    public String getId() { return id.toHexString(); }
    public void  setId(ObjectId id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getTeacher() { return this.teacher.toHexString(); }
    public void setTeacher(ObjectId teacher) { this.teacher = teacher; }

    public List<String> getStudents() {
        List<String> stringIds = new ArrayList<>();
        for (ObjectId student : students) stringIds.add(student.toHexString());
        return stringIds;
    }
    public void setStudents(List<ObjectId> students) {this.students = students;}
    public void addStudent(ObjectId student) { this.students.add(student); }
    public void removeStudent(ObjectId student) { this.students.remove(student); }

    public List<String> getTests() {
        List<String> stringIds = new ArrayList<>();
        for (ObjectId test : tests) stringIds.add(test.toHexString());
        return stringIds;
    }
    public void setTests(List<ObjectId> tests) { this.tests = tests; }
    public void addTest(ObjectId test) { this.tests.add(test); }
    public void removeTest(ObjectId test) { this.tests.remove(test); }
}
