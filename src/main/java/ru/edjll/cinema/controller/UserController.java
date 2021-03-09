package ru.edjll.cinema.controller;

import com.sun.mail.imap.protocol.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.service.CountryService;
import ru.edjll.cinema.service.SubscribeService;
import ru.edjll.cinema.service.UserService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping("/profile")
    public String getUserProfilePage(
            @AuthenticationPrincipal User user,
            Model model
    ) {

        model.addAttribute(user);

        return "user/profile";
    }

    @GetMapping("/profile/update")
    public String getUpdateUserProfilePage(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {

        model.put("countries", countryService.getCountries());
        model.put("user", user);

        return "user/update";
    }

    @PostMapping("/profile/update")
    public String getUpdateUserProfilePage(
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            userService.updateUser(user);
            securityUtils.updateAuthenticationToken();
            return "redirect:/profile";
        }

        model.addAttribute("countries", countryService.getCountries());

        return "user/update";
    }

    @GetMapping("/profile/films")
    public String getUserFilmsPage(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {

        model.put("user", user);

        return "user/films";
    }

    @GetMapping("/profile/subscribes")
    @Transactional
    public String getUserSubscribesPage(
            @AuthenticationPrincipal User authorizedUser,
            Map<String, Object> model
    ) {

        User user = userService.getUserById(authorizedUser.getId());
        Set<UserSubscribe> userSubscribes = user.getSubscribes();
        List<Long> ids = userSubscribes.stream().map(userSubscribe -> userSubscribe.getSubscribePeriod().getId()).collect(Collectors.toList());
        userSubscribes.forEach(UserSubscribe::getSubscribePeriod);
        List<Subscribe> subscribes = subscribeService.getSubscribesWithActivePeriodsAndWithoutSubscribePeriods(ids);

        model.put("boughtSubscribes", userSubscribes);
        model.put("subscribes", subscribes);

        return "user/subscribes";
    }

    @GetMapping("/profile/promo")
    public String getPromoPage() {
        return "user/promo";
    }
}
