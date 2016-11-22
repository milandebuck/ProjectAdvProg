package App

import db.EntryRepository

/**
 * Created by Domien on 22-11-2016.
 */
class GetWordsResponseTest extends GroovyTestCase {
    private void testAnswerChecker(){
        String answer = "plane";
        GetWordsResponse response = new GetWordsResponse();
        EntryRepository repository;
        assertEquals(true, response.checkResponse(repository, 1, answer));
    }
}
