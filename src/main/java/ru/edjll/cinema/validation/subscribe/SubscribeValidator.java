package ru.edjll.cinema.validation.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import ru.edjll.cinema.domain.Film;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.domain.SubscribePeriod;
import ru.edjll.cinema.service.SubscribePeriodService;
import ru.edjll.cinema.service.SubscribeService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubscribeValidator {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private SubscribePeriodService subscribePeriodService;

    public void creatingSubscribeValidate(
            Subscribe subscribe,
            List<Film> films,
            List<SubscribePeriod> subscribePeriods,
            Errors errors
    ) {
        if (films == null) {
            errors.rejectValue("films", "size", "error.validation.subscribe.films.size");
        }
        if (subscribePeriods == null || subscribePeriods.isEmpty()) {
            errors.rejectValue("periods", "size", "error.validation.subscribe.periods.size");
        }
    }

    public void updatingSubscribeValidate(
            Subscribe subscribe,
            List<Film> films,
            List<SubscribePeriod> subscribePeriods,
            Errors errors
    ) {
        if (films == null) {
            errors.rejectValue("films", "size", "error.validation.subscribe.films.size");
        }
        if (subscribePeriods == null || subscribePeriods.isEmpty()) {
            errors.rejectValue("periods", "size", "error.validation.subscribe.periods.size");
        } else {
            Subscribe subscribeFromDB = subscribeService.getById(subscribe.getId());
            List<SubscribePeriod> subscribePeriodsWithPromo = subscribePeriodService.getActiveSubscribePeriodsWithPromo(
                    subscribeFromDB.getPeriods()
                            .stream()
                            .map(SubscribePeriod::getId)
                            .collect(Collectors.toList())
            );
            subscribePeriodsWithPromo.forEach(subscribePeriodWithPromo -> {
                if (!subscribePeriods.contains(subscribePeriodWithPromo)) {
                    errors.rejectValue("periods", "use", "error.validation.subscribe.periods.use");
                } else if (!subscribePeriods.get(subscribePeriods.indexOf(subscribePeriodWithPromo)).isActive()) {
                    errors.rejectValue("periods", "use", "error.validation.subscribe.periods.use");
                }
            });
        }
    }
}
