package App.registration;

import App.logic.Tools;
import App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserRepository userRepo;
    //Configuration DB connection.
    MongoOperations mongoOperations = Tools.getMongoOperations();

    @Override
    public boolean supports(Class<?> aClass) {
        return model.User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        model.User user = (model.User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty", "Username cannot be empty!");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username", "Username length has to be < 6 and > 32!");
        }

        if (userRepo.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username", "Username already exists!");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "Password cannot be empty!");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password", "Password length has to be <6 and > 32!");

        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm" , "Passwords do not match!");
        }
    }
}