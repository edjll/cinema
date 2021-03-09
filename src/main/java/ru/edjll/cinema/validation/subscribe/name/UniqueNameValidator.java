package ru.edjll.cinema.validation.subscribe.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.service.SubscribeService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, Subscribe> {

    @Autowired
    private SubscribeService subscribeService;

    @Override
    public boolean isValid(Subscribe subscribe, ConstraintValidatorContext ctx) {
        Subscribe subscribeFromDB = subscribeService.getSubscribeByName(subscribe.getName());
        if (subscribeFromDB == null || subscribeFromDB.getId().equals(subscribe.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.subscribe.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}