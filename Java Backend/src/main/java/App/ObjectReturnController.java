package App;

import App.logic.GetObject;
import model.Wrapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Robbe De Geyndt on 12/12/2016.
 */
@CrossOrigin
@RestController
public class ObjectReturnController {

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/GetListNames")
    public Wrapper getListNames(@RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GetObject(user).listNames();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/GetList")
    public Wrapper getList(@RequestParam("id") String input, @RequestParam("token") String token) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GetObject(user).getList(input);
    }
}
