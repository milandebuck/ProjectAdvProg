package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by micha on 12/13/2016.
 */
public class ListUsers implements Serializable {

    private List<String> listOfUsernames;

    public ListUsers() {
        super();
    }

    public ListUsers(List<String> usernames){
        setListOfUsernames(usernames);
    }
    public List<String> getListOfUsernames() {
        return listOfUsernames;
    }

    public void setListOfUsernames(List<String> listOfUsernames) {
        this.listOfUsernames = listOfUsernames;
    }

}
