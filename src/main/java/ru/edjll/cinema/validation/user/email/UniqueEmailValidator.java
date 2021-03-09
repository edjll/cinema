package ru.edjll.cinema.validation.user.email;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, User> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext ctx) {
        User dbUser = userService.getUserByEmail(user.getEmail());
        if (dbUser == null || dbUser.getId().equals(user.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.user.email.unique")
                .addPropertyNode("email")
                .addConstraintViolation();
        return false;
    }
}