package model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Mongodocument for a user.
 */
@Document(collection = "users")
public class User {

    private String id;
    @NotNull
    private String username;
    @Transient
    @NotNull
    private String password;
    @Transient
    private String passwordConfirm;
    private boolean teacher;
    private List<ObjectId> wordLists;
    private List<Result> results;

    /**
     * Empty constructor for User object.
     */
    public User(){
        this.username = "";
        this.password = "";
        this.teacher = false;
    }

    /**
     * Constructor for User object, standard not a teacher.
     * @param username user's screen name
     * @param password hash of password
     */
    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.teacher = false;
    }

    /**
     * Constructor for User object.
     * @param username user's screen name
     * @param password hash of password
     * @param teacher is true when user is a teacher
     */
    public User(String username, String password, boolean teacher){
        this.username = username;
        this.password = password;
        this.teacher = teacher;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ObjectId> getWordLists() { return wordLists; }

    public void setWordLists(List<ObjectId> wordLists) { this.wordLists = wordLists; }

    public void addToWordLists(ObjectId wordList) { this.wordLists.add(wordList); }
    public void removeFromWordLists(ObjectId wordList) { this.wordLists.remove(wordList); }

    public List<Result> getResults() { return results; }

    public void setResults(List<Result> results) { this.results = results; }

    public void addResult(Result result) { this.results.add(result); }
    public void removeResult(Result result) {this.results.remove(result); }

    //TODO: ToString
}
