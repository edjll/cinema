package ru.edjll.cinema.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.edjll.cinema.domain.User;

@Component
public class UserValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        User user = (User) object;

        if (user.getPassword().length() < 5 || user.getPassword().length() > 10) {
            errors.rejectValue("password", "length", "error.validation.user.password.length");
        }
        if (user.getRepeatPassword().length() < 5 || user.getRepeatPassword().length() > 10) {
            errors.rejectValue("repeatPassword", "length", "error.validation.user.password.length");
        } else if (!user.getRepeatPassword().equals(user.getPassword())) {
            errors.rejectValue("repeatPassword", "identity", "error.validation.user.password.identity");
        }
    }
}