package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.repository.SubscribeRepo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SubscribeService {

    @Autowired
    private SubscribeRepo subscribeRepo;

    @Autowired
    private SubscribePeriodService subscribePeriodService;

    @Autowired UserService userService;

    public List<Subscribe> getSubscribes() {
        return subscribeRepo.findAll();
    }

    public List<Subscribe> getActiveSubscribes() {
        return subscribeRepo.findAllWithActivePeriods();
    }

    public Subscribe save(Subscribe subscribe, List<Film> films, List<SubscribePeriod> subscribePeriods) {
        subscribePeriods.forEach(
                subscribePeriod -> {
                    if (subscribePeriod.getId() != null) subscribe.getPeriods().add(subscribePeriod);
                    else subscribe.getPeriods().add(subscribePeriodService.save(subscribePeriod));
                }
        );

        if (films != null && !films.isEmpty()) subscribe.setFilms(new HashSet<>(films));
        return subscribeRepo.save(subscribe);
    }

    public Subscribe getById(Long id) {
        return subscribeRepo.findById(id).orElse(null);
    }

    public void delete(Subscribe subscribe) {

        subscribe.getPeriods().forEach(period -> subscribePeriodService.delete(period));

        subscribe.getFilms().clear();

        subscribeRepo.delete(subscribe);
    }

    public Integer getActiveSubscribersCount(Long id) {
        return subscribeRepo.getActiveSubscribesCount(id).orElse(0);
    }

    public List<Subscribe> getSubscribesById(List<Long> ids) {
        if (ids == null) return null;
        return subscribeRepo.findAllByIdIn(ids);
    }

    public Subscribe update(Subscribe subscribe, List<Film> films, List<SubscribePeriod> subscribePeriods) {
        Subscribe subscribeFromDB = getById(subscribe.getId());
        subscribeFromDB.getPeriods().forEach(subscribePeriod -> {
            if (!subscribePeriods.contains(subscribePeriod)) {
                subscribePeriodService.delete(subscribePeriod);
            }
        });
        return save(subscribe, films, subscribePeriods);
    }

    public Subscribe getSubscribeByName(String name) {
        return subscribeRepo.findByName(name);
    }

    public List<Subscribe> getSubscribesWithOneFilmByFilm(Long filmId) {
        return subscribeRepo.findSubscribesWithOneFilmByFilm(filmId);
    }

    public Integer getCountSubscribesWithOneFilmByFilm(Long filmId) {
        return subscribeRepo.getCountSubscribesWithOneFilmByFilm(filmId).orElse(0);
    }

    public List<Subscribe> getSubscribesWithActivePeriodsAndWithoutSubscribePeriods(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return subscribeRepo.findAllWithActivePeriods();
        return subscribeRepo.findAllWithActivePeriodsAndWithoutSubscribePeriods(ids);
    }

    public List<Subscribe> getActiveSubscribesByFilm(Long filmId) {
        if (filmId == null) return null;
        return subscribeRepo.findActiveSubscribesByFilm(filmId);
    }
}
