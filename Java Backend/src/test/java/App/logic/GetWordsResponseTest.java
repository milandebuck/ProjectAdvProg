package App.logic;

/**
 * Test for getting list of random words
 * Created by Domien on 22-11-2016.
 */

/*
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
}*/
