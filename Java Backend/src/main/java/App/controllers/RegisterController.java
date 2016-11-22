package App.controllers;

import App.Authentication.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by micha on 11/21/2016.
 */

@RestController
public class RegisterController {

    //@Autowired
    //private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {

        return "registration page";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationPost() {
/*        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "registration successful";*/

        return "registration succ";
    }
}
