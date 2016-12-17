package App;

import App.logic.GetObject;
import model.Wrapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for getting quick access to database.
 * Available on "/".
 * Created by Robbe De Geyndt on 12/12/2016.
 */
@CrossOrigin
@RestController
public class ObjectReturnController {

    /**
     * Gives back all names and ids from tests from user. [GET]
     * Available on "/GetListNames".
     * @param token token for authentication
     * @return list of testnames and ids ex: [{"name" : "XXX", "id": "XXX"}, {...}]
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/GetListNames")
    public Wrapper getListNames(@RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GetObject(user).listNames();
    }

    /**
     * Gives back specific test. [GET]
     * Available on "/GetList".
     * @param id id from test
     * @param token token for authentication
     * @return test ex: {"name" : "XXX", "list" : [...]}
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/GetList")
    public Wrapper getList(@RequestParam("id") String id, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GetObject(user).getList(id);
    }

    /**
     * Gives back search results for user. [GET]
     * Username does not need to be complete and search is case insensitive.
     * Available on "/SearchUser".
     * @param name string that user wants to search
     * @param token token for authentication
     * @return list of users that match the query ex: [{"username" : "XXX", "id" : "XXX"}, {...}]
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/SearchUser")
    public Wrapper searchUser(@RequestParam("name") String name, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GetObject(user).getUser(name);
    }
}
