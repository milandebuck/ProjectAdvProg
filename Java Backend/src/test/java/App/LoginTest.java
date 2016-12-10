package App;

import App.authentication.UserService;
import App.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for authentication
 * Created by micha on 11/22/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LoginTest {

    @Autowired
    UserService userService;
    public void setUpUsers() {
        userService.save(new model.User("UnitTestUser", "UnitTestPassword"));
    }

    @Test
    public void TestLoadUsernameIfExist() {
        setUpUsers();
        User user = userService.loadUserByUsername("UnitTestUser");
        assertNotNull("Correct user is in the database", user);


    }
}
