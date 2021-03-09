package ru.edjll.cinema.validation.promo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ru.edjll.cinema.domain.Promo;
import ru.edjll.cinema.domain.SubscribePeriod;
import ru.edjll.cinema.service.PromoService;

import java.util.List;

@Service
public class PromoValidator {

    public  void creatingPromoValidate(Promo promo, List<SubscribePeriod> subscribePeriods, Errors errors) {
        updatingPromoValidate(promo, subscribePeriods, errors);
    }

    public void updatingPromoValidate(Promo promo, List<SubscribePeriod> subscribePeriods, Errors errors) {
        if (subscribePeriods == null) {
            errors.rejectValue("subscribes", "size", "error.validation.promo.subscribes.size");
        }
    }
}