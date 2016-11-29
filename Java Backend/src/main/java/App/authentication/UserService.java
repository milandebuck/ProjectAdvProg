package App.authentication;

import db.UserRepository;
import java.util.ArrayList;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;

    public UserService() {
    }

    public void save(User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        user.setTeacher(false);
        this.userRepo.save(user);
    }

    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        User userModel = this.userRepo.findByUsername(username);
        if (userModel != null) {
            org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(userModel.getUsername(), userModel.getPassword(), new ArrayList());
            return springUser;
        } else {
            return null;
        }
    }
}