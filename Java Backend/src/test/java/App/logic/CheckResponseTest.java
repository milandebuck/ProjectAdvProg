package App.logic;

import junit.framework.TestCase;
import model.Entry;
import model.Wrapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test for word checker.
 * Created by Robbe De Geyndt on 26/11/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {config.MongoConfig.class, App.Application.class})
public class CheckResponseTest extends TestCase {

    private List<Entry> entries1 = new ArrayList<Entry>();

    /**
     * Puts testdata in Array for testing.
     */
    private void setup() {
        //Correct entries
        entries1.add(new Entry("(aero)plane", "het vliegtuig", new String[]{"English", "Dutch"}));
        entries1.add(new Entry("(arch)angel", "de (aarts)engel", new String[]{"English", "Dutch"}));

        //Faulty entries
        entries1.add(new Entry("(blood) circulation", "wrong answer", new String[]{"English", "Dutch"}));
        entries1.add(new Entry("(bride)groom", "wrong answer", new String[]{"English", "Dutch"}));
    }

    /**
     * Check if API rejects faulty input.
     */
    @Test
    public void testRejectFaultyInput() {
        setup();

        Wrapper<List<Entry>> wrapper1 = new Wrapper<List<Entry>>();
        wrapper1.setData(entries1);
        wrapper1.setSucces(false);

        CheckResponse cr1 = new CheckResponse(new JSONObject(wrapper1).toString());

        assertEquals("Incorrect input", cr1.getResult().getMsg());
    }

    /**
     * Test if score was correctly calculated.
     */
    @Test
    public void testCalculateScore() {
        setup();

        Wrapper<List<Entry>> wrapper2 = new Wrapper<List<Entry>>();
        wrapper2.setData(entries1);
        wrapper2.setSucces(true);

        CheckResponse cr2 = new CheckResponse(new JSONObject(wrapper2).toString());

        Map<String,String> obj = (HashMap<String,String>)cr2.getResult().getData();

        assertEquals(2, obj.get("score"));
        assertEquals(4, obj.get("max"));
    }

    /**
     * Test if the corrections are returned.
     */
    @Test
    public void testFeedback() {
        setup();

        Wrapper<List<Entry>> wrapper3 = new Wrapper<List<Entry>>();
        wrapper3.setData(entries1);
        wrapper3.setSucces(true);


        CheckResponse cr3 = new CheckResponse(new JSONObject(wrapper3).toString());

        Map obj = (HashMap)cr3.getResult().getData();

        List<Entry> faulty = (List<Entry>)obj.get("faulty");

        assertEquals(2, faulty.size());
        assertEquals("(blood) circulation", faulty.get(0).getWord());
        assertEquals("(bride)groom", faulty.get(1).getWord());
    }
}