package ru.edjll.cinema.validation.promo.activation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.cinema.domain.Promo;
import ru.edjll.cinema.service.PromoService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ActivationCountValidator implements ConstraintValidator<ActivationCount, Promo> {

    @Autowired
    private PromoService promoService;

    @Override
    public boolean isValid(Promo promo, ConstraintValidatorContext ctx) {
        if (promo.getActivationCount() == null) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("error.validation.promo.activationCount.null")
                    .addPropertyNode("activationCount")
                    .addConstraintViolation();
            return false;
        } else if (promo.getActivationCount() >= 1 && promo.getActivationCount() <= 1000) {
            if (promoService.getCountActivation(promo.getId()) < promo.getActivationCount()) {
                return true;
            } else {
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate("error.validation.promo.activationCount.activated")
                        .addPropertyNode("activationCount")
                        .addConstraintViolation();
                return false;
            }
        } else {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("error.validation.promo.activationCount.size")
                    .addPropertyNode("activationCount")
                    .addConstraintViolation();
            return false;
        }
    }
}