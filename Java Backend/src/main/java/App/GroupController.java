package App;

import App.logic.GroupInteraction;
import model.Wrapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Robbe De Geyndt on 14/12/2016.
 */
@CrossOrigin
@RestController
@RequestMapping("/Group")
public class GroupController {

    @CrossOrigin
    @RequestMapping(value = "/SaveGroup", method = RequestMethod.POST)
    public Wrapper createGroup(@RequestBody String input, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).CreateGroup(input);
    }

    @CrossOrigin
    @RequestMapping(value = "/PublishTest", method = RequestMethod.GET)
    public Wrapper publishTest(@RequestParam("groupid") String groupid, @RequestParam("testid") String testid, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).PublishTest(testid, groupid);
    }

    @CrossOrigin
    @RequestMapping(value = "/GetResults", method = RequestMethod.GET)
    public Wrapper testResults(@RequestParam("groupid") String groupid, @RequestParam("testid") String testid, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).TestResults(testid, groupid);
    }

    @CrossOrigin
    @RequestMapping(value = "/AddStudents", method = RequestMethod.POST)
    public Wrapper addStudents(@RequestBody String input, @RequestParam("groupid") String groupid, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GroupInteraction(user).AddStudents(input, groupid);
    }

}
