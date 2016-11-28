package App.logic;

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

        Wrapper wrapper1 = new Wrapper(entries1);
        wrapper1.setValid(false);

        CheckResponse cr1 = new CheckResponse(repository, new JSONObject(wrapper1).toString());

        assertEquals("Incorrect input", cr1.getResult().getMsg());
    }

    @Test
    public void testCalculateScore() {
        setup();

        Wrapper wrapper2 = new Wrapper(entries1);

        CheckResponse cr2 = new CheckResponse(repository, new JSONObject(wrapper2).toString());

        Map<String,String> obj = (HashMap<String,String>)cr2.getResult().getObject();

        assertEquals(2, obj.get("score"));
        assertEquals(4, obj.get("max"));
    }

    @Test
    public void testFeedback() {
        setup();

        Wrapper wrapper3 = new Wrapper(entries1);
        CheckResponse cr3 = new CheckResponse(repository, new JSONObject(wrapper3).toString());

        Map obj = (HashMap)cr3.getResult().getObject();

        List<Entry> faulty = (List<Entry>)obj.get("faulty");

        assertEquals(2, faulty.size());
        assertEquals("(blood) circulation", faulty.get(0).getWord());
        assertEquals("(bride)groom", faulty.get(1).getWord());
    }
}*/
