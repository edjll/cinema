package ru.edjll.cinema.validation.promo.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.cinema.domain.Promo;
import ru.edjll.cinema.service.PromoService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, Promo> {

    @Autowired
    private PromoService promoService;

    @Override
    public boolean isValid(Promo promo, ConstraintValidatorContext ctx) {
        Promo promoFromDB = promoService.getPromoByName(promo.getName());
        if (promoFromDB == null || promoFromDB.getId().equals(promo.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.promo.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}