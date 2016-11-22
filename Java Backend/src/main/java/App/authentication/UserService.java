package App.authentication;

import db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    public void save(model.User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {

        model.User userModel = userRepo.findByUsername(username);

        System.out.println("this is the username:" + username);
        //TODO still wondering whether to use a custom query for this
        //TODO IMPORTANT userMODEL = null when nothing is found, this will give a nullpointerxception!!!!
        //We're not using roles yet so roles is empty
        User springUser = new User(userModel.getUsername(), userModel.getPassword(), new ArrayList<GrantedAuthority>());

        return springUser;
    }
}