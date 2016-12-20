import App.authentication.UserService;
import App.repository.UserRepository;
import junit.framework.TestCase;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by micha on 12/19/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.configuration.WebConfig.class, App.Application.class, App.logic.Tools.class})
@WebAppConfiguration
public class AuthenticationTest extends TestCase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Before
    public void setup() {
        //store testuser in db
        User user = new User("testuser", "testpassword");
        userService.save(user);


    }

    @Test
    public void testUserLogin() {

    }

    @After
    public void tearDown() {
        userRepo.deleteByUsername("testuser");
    }

}
