package App;

import App.logic.GetObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Robbe De Geyndt on 10/12/2016.
 */
@CrossOrigin
@RestController
public class GetObjectController {
    @CrossOrigin
    @GetMapping("/GetUser")
    public String saveList() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return new GetObject(user).user();
    }
}
