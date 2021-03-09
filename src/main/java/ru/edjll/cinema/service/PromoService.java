package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.cinema.domain.Promo;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.domain.UserSubscribe;
import ru.edjll.cinema.repository.PromoRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PromoService {

    @Autowired
    private PromoRepo promoRepo;

    @Autowired
    private UserSubscribeService userSubscribeService;

    public Promo save(Promo promo) {
        return promoRepo.save(promo);
    }

    public Promo update(Promo promo) {
        Promo promoFromBD = getPromoById(promo.getId());
        promoFromBD.setName(promo.getName());
        promoFromBD.setSubscribes(promo.getSubscribes());
        return save(promoFromBD);
    }

    public Promo getPromoByName(String name) {
        return promoRepo.findByName(name);
    }

    public List<Promo> getPromos() {
        return promoRepo.findAll();
    }

    public Integer getCountActivation(Long id) {
        return promoRepo.getCountActivation(id).orElse(0);
    }

    public Promo getPromoById(Long id) {
        return promoRepo.findById(id).orElse(null);
    }

    public List<UserSubscribe> activatePromo(User user, Promo promo) {
        List<UserSubscribe> userSubscribes = new ArrayList<>();
        if (promo.getUsers() == null) promo.setUsers(Collections.singletonList(user));
        else promo.getUsers().add(user);
        promo.getSubscribes().forEach(subscribePeriod -> {
            userSubscribes.add(userSubscribeService.createUserSubscribe(subscribePeriod, user));
        });
        return userSubscribes;
    }

    public void delete(Promo promo) {
        promo.getUsers().clear();
        promo.getSubscribes().clear();
        promoRepo.delete(promo);
    }

    public Boolean alreadyActivate(Long promoId, Long userId) {
        Integer result = promoRepo.alreadyActivate(promoId, userId).orElse(0);
        return result == 1;
    }
}
