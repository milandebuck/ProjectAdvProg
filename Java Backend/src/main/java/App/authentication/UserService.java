package App.authentication;

import App.repository.UserRepository;
import App.configuration.MongoConfig;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepo;

    public UserService() {

    }

    /**
     *   Save the user in the database with username, password and teacherboolean
     **/
    public void save(User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        user.setTeacher(false);
        this.userRepo.save(user);
    }

    /**
     *   Load the username, if it exists returns the username with password and isTeacher
     **/
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {

        User userModel = userRepo.findByUsername(username);
        if (userModel != null) {
            org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(userModel.getUsername(), userModel.getPassword(), new ArrayList());
            return springUser;
        } else {
            return null;
        }
    }
}
