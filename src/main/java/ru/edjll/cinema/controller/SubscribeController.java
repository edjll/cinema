package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.service.*;
import ru.edjll.cinema.validation.subscribe.SubscribeValidator;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private SubscribePeriodService subscribePeriodService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSubscribeService userSubscribeService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private SubscribeValidator subscribeValidator;

    @GetMapping("/admin/subscribe/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getCreateSubscribePage(Map<String, Object> model) {

        List<Film> films = filmService.getFilms();

        model.put("films", films);

        return "subscribe/create";
    }

    @PostMapping("/admin/subscribe/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createSubscribe(
            @Valid Subscribe subscribe,
            BindingResult bindingResult,
            @RequestParam(name = "period[]", required = false) List<Integer> periods,
            @RequestParam(name = "price[]", required = false) List<Double> prices,
            @RequestParam(name = "film[]", required = false) List<Long> filmsId,
            Model model
    ) {

        List<SubscribePeriod> subscribePeriods = subscribePeriodService.convertToSubscribePeriods(periods, prices);
        List<Film> films = filmService.getFilmsById(filmsId);

        subscribeValidator.creatingSubscribeValidate(subscribe, films, subscribePeriods, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            subscribeService.save(subscribe, films, subscribePeriods);
            return "redirect:/admin/subscribes";
        }

        model.addAttribute("films", filmService.getFilms());
        model.addAttribute("checkedFilms", films);

        return "subscribe/create";
    }

    @GetMapping("/admin/subscribe/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUpdateSubscribePage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Subscribe subscribe = subscribeService.getById(id);
        List<Integer> countActiveSubscribersInSubscribePeriods = subscribe.getPeriods()
                .stream().map(subscribePeriod -> subscribePeriodService.getCountActiveSubscribers(subscribePeriod.getId()))
                .collect(Collectors.toList());
        List<Film> films = filmService.getFilmsWithoutSubscribe(subscribe);

        model.put("films", films);
        model.put("subscribe", subscribe);
        model.put("countActiveSubscribers", countActiveSubscribersInSubscribePeriods);

        return "subscribe/update";
    }

    @PostMapping("/admin/subscribe/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateSubscribe(
            @Valid Subscribe subscribe,
            BindingResult bindingResult,
            @RequestParam(name = "period[]", required = false) List<Integer> periods,
            @RequestParam(name = "price[]", required = false) List<Double> prices,
            @RequestParam(name = "film[]", required = false) List<Long> filmsId,
            @RequestParam(name = "periodId[]", required = false) List<Long> periodsId,
            @RequestParam(name = "periodActive[]", required = false) List<Boolean> periodsActive,
            Model model
    ) {

        Subscribe subscribeFromDB = subscribeService.getById(subscribe.getId());

        if (subscribeFromDB == null) {
            model.addAttribute("message", "Подписка с таким id не найдена");
            return "message";
        }

        List<SubscribePeriod> subscribePeriods = subscribePeriodService.convertToSubscribePeriods(periods, prices, periodsActive, periodsId);
        List<Film> films = filmService.getFilmsById(filmsId);

        subscribeValidator.updatingSubscribeValidate(subscribe, films, subscribePeriods, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            subscribeService.update(subscribe, films, subscribePeriods);
            return "redirect:/admin/subscribes";
        }

        List<Integer> countActiveSubscribersInSubscribePeriods = subscribeFromDB.getPeriods()
                .stream().map(subscribePeriod -> subscribePeriodService.getCountActiveSubscribers(subscribePeriod.getId()))
                .collect(Collectors.toList());

        model.addAttribute("subscribe", subscribeFromDB);
        model.addAttribute("films", filmService.getFilmsWithoutSubscribe(subscribeFromDB));
        model.addAttribute("countActiveSubscribers", countActiveSubscribersInSubscribePeriods);

        return "subscribe/update";
    }

    @GetMapping("/admin/subscribe/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getDeleteSubscribePage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Subscribe subscribe = subscribeService.getById(id);

        if (subscribe == null) {
            model.put("message", "Подписка с таким id не найдена");
            return "message";
        }
        if (subscribeService.getActiveSubscribersCount(subscribe.getId()) > 0) {
            model.put("message", "Подписку с активными подписчиками невозможно удалить, но можно отключить её периоды");
            return "message";
        }

        model.put("subscribe", subscribe);

        return "subscribe/delete";
    }

    @PostMapping("/admin/subscribe/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteSubscribe(
            @RequestParam Long id,
            Map<String, Object> model
    ) {

        Subscribe subscribe = subscribeService.getById(id);

        if (subscribe == null) {
            model.put("message", "Подписка с таким id не найдена");
        }
        if (subscribeService.getActiveSubscribersCount(subscribe.getId()) > 0) {
            model.put("message", "Подписку с активными подписчиками невозможно удалить, но можно отключить её периоды");
            return "message";
        }

        subscribeService.delete(subscribe);

        return "redirect:/admin/subscribes";
    }

    @PostMapping("/subscribe/buy")
    @Transactional
    public String buySubscribe(
            @AuthenticationPrincipal User authorizeUser,
            @RequestParam Long subscribePeriodId,
            Map<String, Object> model
    ) {

        User user = userService.getUserById(authorizeUser.getId());
        SubscribePeriod subscribePeriod = subscribePeriodService.getById(subscribePeriodId);

        if (subscribePeriod == null) {
            model.put("message", "Подписка не найдена");
            return "message";
        }

        userSubscribeService.createUserSubscribe(subscribePeriod, user);

        return "redirect:/films/subscribe/" + subscribePeriod.getSubscribe().getId();
    }
}
