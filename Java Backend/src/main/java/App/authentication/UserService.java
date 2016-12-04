package App.authentication;

import App.logic.Tools;
import App.testingrepo.UserRepository;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserService implements UserDetailsService {

    private MongoOperations userRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repo;

    public UserService() {
        userRepo = Tools.getMongoOperations();
    }

    /**
     *   Save the user in the database with username, password and teacherboolean
     **/
    public void save(User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        user.setTeacher(false);
        this.userRepo.save(user, "users");
    }

    /**
     *   Load the username, if it exists returns the username with password and isTeacher
     **/
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {

        //Make query
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(username));


        //User userModel = this.userRepo.findOne(getUser, User.class, "users");
        User userModel = repo.findByUsername(username);
        if (userModel != null) {
            org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(userModel.getUsername(), userModel.getPassword(), new ArrayList());
            return springUser;
        } else {
            return null;
        }
    }
}
