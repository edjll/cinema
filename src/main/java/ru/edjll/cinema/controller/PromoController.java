package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.cinema.domain.Promo;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.domain.SubscribePeriod;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.service.PromoService;
import ru.edjll.cinema.service.SubscribePeriodService;
import ru.edjll.cinema.service.SubscribeService;
import ru.edjll.cinema.service.UserSubscribeService;
import ru.edjll.cinema.validation.promo.PromoValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PromoController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private SubscribePeriodService subscribePeriodService;

    @Autowired
    private PromoService promoService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private PromoValidator promoValidator;

    @GetMapping("/admin/promo/create")
    public String getCreatePromoPage(Map<String, Object> model) {

        List<Subscribe> subscribes = subscribeService.getActiveSubscribes();
        subscribes.forEach(Subscribe::getActiveSubscribePeriods);

        model.put("subscribes", subscribes);

        return "promo/create";
    }

    @PostMapping("/admin/promo/create")
    @Transactional
    public String createPromo(
            @Valid Promo promo,
            BindingResult bindingResult,
            @RequestParam(name = "subscribePeriod[]", required = false) List<Long> subscribePeriodsId,
            Model model
    ) {

        List<SubscribePeriod> subscribePeriods = subscribePeriodService.getSubscribePeriodsById(subscribePeriodsId);

        promoValidator.creatingPromoValidate(promo, subscribePeriods, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            promo.setSubscribes(subscribePeriods);
            promoService.save(promo);
            return "redirect:/admin/promo";
        }

        model.addAttribute("subscribes", subscribeService.getActiveSubscribes());

        return "promo/create";
    }

    @GetMapping("/admin/promo/{id}/update")
    @Transactional
    public String getUpdatePromoPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Promo promo = promoService.getPromoById(id);

        if (promo == null) {
            model.put("message", "Промо с таким id не существует");
            return "message";
        }

        model.put("subscribes", subscribeService.getSubscribesWithActivePeriodsAndWithoutSubscribePeriods(
                promo.getSubscribes()
                        .stream()
                        .map(SubscribePeriod::getId)
                        .collect(Collectors.toList()))
        );
        model.put("promo", promo);

        return "promo/update";
    }

    @PostMapping("/admin/promo/update")
    @Transactional
    public String updatePromo(
            @Valid Promo promo,
            BindingResult bindingResult,
            @RequestParam(name = "subscribePeriod[]", required = false) List<Long> subscribePeriodsId,
            Model model
    ) {

        Promo promoFromDB = promoService.getPromoById(promo.getId());

        if (promoFromDB == null) {
            model.addAttribute("message", "Промо с таким id не найден");
            return "message";
        }

        List<SubscribePeriod> subscribePeriods = subscribePeriodService.getSubscribePeriodsById(subscribePeriodsId);

        promoValidator.updatingPromoValidate(promo, subscribePeriods, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            promo.setSubscribes(subscribePeriods);
            promoService.update(promo);
            return "redirect:/admin/promo";
        }

        model.addAttribute("promo", promoFromDB);
        model.addAttribute("subscribes", subscribeService.getActiveSubscribes());

        return "promo/update";
    }

    @GetMapping("/admin/promo/{id}/delete")
    @Transactional
    public String getDeletePromoPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Promo promo = promoService.getPromoById(id);

        if (promo == null) {
            model.put("message", "Промо с таким id не существует");
            return "message";
        }

        model.put("promo", promo);

        return "promo/delete";
    }

    @PostMapping("/admin/promo/delete")
    @Transactional
    public String deletePromo(
            @RequestParam Long id,
            Map<String, Object> model
    ) {

        Promo promo = promoService.getPromoById(id);

        if (promo == null) {
            model.put("message", "Промо с таким id не найден");
            return "message";
        }

        promoService.delete(promo);

        return "redirect:/admin/promo";
    }

    @PostMapping("/profile/promo")
    @Transactional
    public String activatePromo(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            Map<String, Object> model
    ) {

        Promo promo = promoService.getPromoByName(name);

        if (promo == null ||
            promoService.getCountActivation(promo.getId()) >= promo.getActivationCount() ||
            promo.getDateEnd().getTime() < new Date().getTime()
        ) {
            model.put("error", "Данный код не действителен");
            model.put("name", name);

            return "user/promo";
        }
        if (promoService.alreadyActivate(promo.getId(), user.getId())) {
            model.put("error", "Вы уже активировали данный промокод");
            model.put("name", name);

            return "user/promo";
        }

        List<SubscribePeriod> addedSubscribes = new ArrayList<>();
        promoService.activatePromo(user, promo).forEach(userSubscribe -> {
            if (userSubscribe != null) addedSubscribes.add(userSubscribe.getSubscribePeriod());
        });

        model.put("subscribes", promo.getSubscribes());
        model.put("addedSubscribes", addedSubscribes);

        return "user/promo";
    }
}
