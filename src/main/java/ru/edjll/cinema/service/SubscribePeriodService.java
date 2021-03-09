package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.domain.SubscribePeriod;
import ru.edjll.cinema.repository.SubscribePeriodRepo;
import ru.edjll.cinema.repository.SubscribeRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubscribePeriodService {

    @Autowired
    private SubscribePeriodRepo subscribePeriodRepo;

    public SubscribePeriod save(SubscribePeriod subscribePeriod) {
        return subscribePeriodRepo.save(subscribePeriod);
    }

    public SubscribePeriod getById(Long id) {
        return subscribePeriodRepo.findById(id).orElse(null);
    }

    public List<SubscribePeriod> convertToSubscribePeriods(List<Integer> periods, List<Double> prices) {
        if (periods == null || prices == null) return null;
        List<SubscribePeriod> subscribePeriods = new ArrayList<>();
        for (int i = 0; i < periods.size(); i++) {
            if (periods.get(i) != null && prices.get(i) != null) {
                subscribePeriods.add(new SubscribePeriod(periods.get(i), prices.get(i), true));
            }
        }
        return subscribePeriods;
    }

    public List<SubscribePeriod> convertToSubscribePeriods(List<Integer> periods, List<Double> prices, List<Boolean> periodsActive, List<Long> periodsId) {
        if (periods == null || prices == null) return null;
        List<SubscribePeriod> subscribePeriods = new ArrayList<>();
        for (int i = 0; i < periods.size(); i++) {
            if (periods.get(i) != null && prices.get(i) != null) {
                SubscribePeriod subscribePeriod = null;
                if (periodsId.get(i) != null) subscribePeriod = getById(periodsId.get(i));
                if (subscribePeriod != null) {
                    if (!subscribePeriod.getPeriod().equals(periods.get(i))) subscribePeriod.setPeriod(periods.get(i));
                    if (!subscribePeriod.getPrice().equals(prices.get(i))) subscribePeriod.setPrice(prices.get(i));
                    if (subscribePeriod.isActive() != periodsActive.get(i)) subscribePeriod.setActive(periodsActive.get(i));
                    subscribePeriods.add(subscribePeriod);
                } else {
                    subscribePeriods.add(new SubscribePeriod(periods.get(i), prices.get(i), true));
                }
            }
        }
        return subscribePeriods;
    }

    public void delete(SubscribePeriod subscribePeriod) {
        subscribePeriodRepo.delete(subscribePeriod);
    }

    public Integer getCountActiveSubscribers(Long id) {
        return subscribePeriodRepo.countByActiveSubscribers(id).orElse(0);
    }

    public List<SubscribePeriod> getSubscribePeriodsById(List<Long> ids) {
        if (ids == null) return null;
        return subscribePeriodRepo.findAllByIdIn(ids);
    }

    public List<SubscribePeriod> getActiveSubscribePeriodsWithPromo(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return null;
        return subscribePeriodRepo.findSubscribePeriodWithPromo(ids);
    }
}
