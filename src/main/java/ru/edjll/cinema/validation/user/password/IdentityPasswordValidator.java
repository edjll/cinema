package ru.edjll.cinema.validation.user.password;

import ru.edjll.cinema.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityPasswordValidator implements ConstraintValidator<IdentityPassword, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext ctx) {
        if (user.getRepeatPassword().equals(user.getPassword())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("Пароли не совпадают")
                .addPropertyNode("repeatPassword")
                .addConstraintViolation();
        return false;
    }
}