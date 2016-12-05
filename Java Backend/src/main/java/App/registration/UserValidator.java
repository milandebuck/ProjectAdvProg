package App.registration;

import App.logic.Tools;
import model.User;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {


    //Configuration DB connection.
    MongoOperations mongoOperations = Tools.getMongoOperations();

    @Override
    public boolean supports(Class<?> aClass) {
        return model.User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        model.User user = (model.User) o;

        //Make query
        Query getUser = new Query();
        getUser.addCriteria(Criteria.where("username").is(user.getUsername()));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (mongoOperations.findOne(getUser, User.class, "users") != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}