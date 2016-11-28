package App.logic;

/*import App.Application;
import config.MongoConfig;
import db.EntryRepository;
import junit.framework.TestCase;
import model.Entry;
import model.Wrapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;*/

/**
 * Test for word checker.
 * Created by Robbe De Geyndt on 26/11/2016.
 */

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EntryRepository.class, MongoConfig.class, Application.class})
public class CheckResponseTest extends TestCase {
    @Autowired
    private EntryRepository repository;
    private List<Entry> entries1 = new ArrayList<Entry>();


    private void setup() {
        //Correct entries
        entries1.add(new Entry("(aero)plane", "het vliegtuig", new String[]{"English", "Dutch"}));
        entries1.add(new Entry("(arch)angel", "de (aarts)engel", new String[]{"English", "Dutch"}));

        //Faulty entries
        entries1.add(new Entry("(blood) circulation", "wrong answer", new String[]{"English", "Dutch"}));
        entries1.add(new Entry("(bride)groom", "wrong answer", new String[]{"English", "Dutch"}));
    }

    @Test
    public void testRejectFaultyInput() {
        setup();

        Wrapper wrapper1 = new Wrapper();
        wrapper1.setData(entries1);
        wrapper1.setSucces(false);

        CheckResponse cr1 = new CheckResponse(repository, new JSONObject(wrapper1).toString());

        assertEquals("Incorrect input", cr1.getResult().getMsg());
    }

    @Test
    public void testCalculateScore() {
        setup();

        Wrapper wrapper2 = new Wrapper();
        wrapper2.setData(entries1);
        wrapper2.setSucces(true);

        CheckResponse cr2 = new CheckResponse(repository, new JSONObject(wrapper2).toString());

        Map<String,String> obj = (HashMap<String,String>)cr2.getResult().getData();

        assertEquals(2, obj.get("score"));
        assertEquals(4, obj.get("max"));
    }

    @Test
    public void testFeedback() {
        setup();

        Wrapper wrapper3 = new Wrapper();
        wrapper3.setData(entries1);
        wrapper3.setSucces(true);


        CheckResponse cr3 = new CheckResponse(repository, new JSONObject(wrapper3).toString());

        Map obj = (HashMap)cr3.getResult().getData();

        List<Entry> faulty = (List<Entry>)obj.get("faulty");

        assertEquals(2, faulty.size());
        assertEquals("(blood) circulation", faulty.get(0).getWord());
        assertEquals("(bride)groom", faulty.get(1).getWord());
    }
}*/
