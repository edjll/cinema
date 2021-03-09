package ru.edjll.cinema.validation.user.username;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.cinema.domain.Category;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext ctx) {
        User dbUser = userService.getUserByUsername(user.getUsername());
        if (dbUser == null || dbUser.getId().equals(user.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.user.username.unique")
                .addPropertyNode("username")
                .addConstraintViolation();
        return false;
    }
}