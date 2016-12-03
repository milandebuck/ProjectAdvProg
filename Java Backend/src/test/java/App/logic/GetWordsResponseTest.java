package App.logic;

import junit.framework.TestCase;
import model.Entry;
import model.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Test for getting list of random words
 * Created by Domien on 22-11-2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {config.MongoConfig.class, App.Application.class})
public class GetWordsResponseTest extends TestCase {
    /**
     * Test if list is generated correctly.
     */
    @Test
    public  void testList(){

        GetWordsResponse gwr = new GetWordsResponse(new String[] {"English", "Dutch"}, "5");

        Wrapper<List<Entry>> response = gwr.listOut();

        List<Entry> randomList = response.getData();

        assertTrue(response.getSucces());
        assertEquals(5,randomList.size());

        for (Entry entry : randomList) {
            assertEquals("English", entry.getLanguages()[0]);
            assertEquals("Dutch", entry.getLanguages()[1]);
        }
    }
}