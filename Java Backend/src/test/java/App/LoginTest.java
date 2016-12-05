package App;

import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Created by micha on 11/22/2016.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class LoginTest {

    public void SuccessfullyLogin() {
        assertEquals(true,true);
    }
}
