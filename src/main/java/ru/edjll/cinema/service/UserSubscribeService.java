package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.cinema.domain.SubscribePeriod;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.domain.UserSubscribe;
import ru.edjll.cinema.repository.UserSubscribeRepo;

import java.util.Date;

@Service
public class UserSubscribeService {

    @Autowired
    private UserSubscribeRepo userSubscribeRepo;

    @Autowired
    private UserService userService;

    public UserSubscribe save(UserSubscribe userSubscribe) {
        return userSubscribeRepo.save(userSubscribe);
    }

    public UserSubscribe createUserSubscribe(SubscribePeriod subscribePeriod, User user) {
        if (!userService.hasSubscribe(subscribePeriod.getSubscribe().getId(), user.getId())) {
            UserSubscribe userSubscribe = new UserSubscribe();
            userSubscribe.setUser(user);
            userSubscribe.setSubscribePeriod(subscribePeriod);
            userSubscribe.setDateStart(new Date());
            userSubscribe.setPeriod(subscribePeriod.getPeriod());
            userSubscribe.setPrice(subscribePeriod.getPrice());
            userSubscribe.setRenewal(true);
            return save(userSubscribe);
        }
        return null;
    }

    public UserSubscribe getById(Long id) {
        return userSubscribeRepo.findById(id).orElse(null);
    }

    public void changeRenewal(UserSubscribe userSubscribe, boolean renewal) {
        userSubscribe.setRenewal(!renewal);
        save(userSubscribe);
    }
}
