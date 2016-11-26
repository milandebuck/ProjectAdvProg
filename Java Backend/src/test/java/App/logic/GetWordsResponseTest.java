package App.logic;

import App.Application;
import config.MongoConfig;
import db.EntryRepository;
import junit.framework.TestCase;
import model.Entry;
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
@ContextConfiguration(classes = {EntryRepository.class, MongoConfig.class, Application.class})
public class GetWordsResponseTest extends TestCase {
    @Autowired
    private EntryRepository repository;

    @Test
    public  void testList(){

        GetWordsResponse gwr = new GetWordsResponse(repository, new String[] {"English", "Dutch"}, "5");

        Wrapper response = gwr.listOut();

        List<Entry> randomList = (List<Entry>)response.getObject();

        assertTrue(response.getValid());
        assertEquals(5,randomList.size());

        for (Entry entry : randomList) {
            assertEquals("English", entry.getLanguages()[0]);
            assertEquals("Dutch", entry.getLanguages()[1]);
        }
    }
}