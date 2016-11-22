package App.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        UsernamePasswordAuthenticationToken sf = new UsernamePasswordAuthenticationToken("name", "password");
        return "Login page";
    }

    //Login post is provided by Spring.


}
