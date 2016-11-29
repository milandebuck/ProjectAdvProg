package App.logic;

import App.authentication.JwtTokenUtil;
import App.authentication.UserService;
import db.EntryRepository;
import junit.framework.TestCase;
import model.Entry;
import model.User;
import model.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test for getting list of random words
 * Created by Domien on 22-11-2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {db.EntryRepository.class, config.MongoConfig.class, App.Application.class})
public class LoginTest extends TestCase {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Test
    public void testLogin() {
        //Store username and password into db
        userService.save(new User("usernametest", "passwordtest", false));
        //org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("")
        //String token = jwtUtil.generateToken()


    }

}