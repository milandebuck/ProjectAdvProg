package App;

import App.logic.GroupInteraction;
import model.Wrapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing groups.
 * Everything in this controller is available on "/Group".
 * Created by Robbe De Geyndt on 14/12/2016.
 */
@CrossOrigin
@RestController
@RequestMapping("/Group")
public class GroupController {

    /**
     * Gives back all users in a group. [GET]
     * Available on "/Group/GetStudents".
     * @param groupid if from goup
     * @param token token for authentication
     * @return list of users ex [{"name" : "XXX", "id" : "XXX"}, {...}]
     */
    @CrossOrigin
    @RequestMapping(value = "/GetStudents", method = RequestMethod.GET)
    public Wrapper getStudents(@RequestParam("groupid") String groupid, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).getStudents(groupid);
    }

    /**
     * Creates a group, only available for teachers. [POST]
     * Available on "/Group/SaveGroup".
     * @param input name of the group ex: {"name" : "XXX"}
     * @param token token for authentication
     * @return confirmation
     */
    @CrossOrigin
    @RequestMapping(value = "/SaveGroup", method = RequestMethod.POST)
    public Wrapper createGroup(@RequestBody String input, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).createGroup(input);
    }

    /**
     * Publishes test in group. [GET]
     * Available on "/Group/PublishTest".
     * @param groupid id from group
     * @param testid id from test
     * @param token token for authentication
     * @return confirmation
     */
    @CrossOrigin
    @RequestMapping(value = "/PublishTest", method = RequestMethod.GET)
    public Wrapper publishTest(@RequestParam("groupid") String groupid, @RequestParam("testid") String testid, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).publishTest(testid, groupid);
    }

    /**
     * Gets results for a test from all students in a specified group. [GET]
     * Available on "/Group/GetResults".
     * @param groupid id from group
     * @param testid id from test
     * @param token token for authentication
     * @return list of results ex: [{"student" : "XXX", "results" : [...]}, {...}]
     */
    @CrossOrigin
    @RequestMapping(value = "/GetResults", method = RequestMethod.GET)
    public Wrapper testResults(@RequestParam("groupid") String groupid, @RequestParam("testid") String testid, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).testResults(testid, groupid);
    }


    /**
     * Add students to group. [POST]
     * Available on "/Group/AddStudents".
     * @param input List of ids of students ex: ["id1", "id2"]
     * @param groupid id of group given as parameter in URL
     * @param token token for authentication
     * @return confirmation
     */
    @CrossOrigin
    @RequestMapping(value = "/AddStudents", method = RequestMethod.POST)
    public Wrapper addStudents(@RequestBody String input, @RequestParam("groupid") String groupid, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).addStudents(input, groupid);
    }

}
