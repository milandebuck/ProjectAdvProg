package model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Mongodocument for a user
 */

@Document(collection = "users")
public class User {

    private String id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Transient
    private String passwordConfirm;
    private boolean teacher;


    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }


    public User(){
        this.username = "";
        this.password = "";
        this.teacher = false;
    }

    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.teacher = false;
    }

    public User(String username, String password, boolean teacher){
        this.username = username;
        this.password = password;
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

    //TODO: ToString
}
