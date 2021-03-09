package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.service.CountryService;
import ru.edjll.cinema.service.RegistrationService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class AuthorizationController {

    @Autowired
    private RegistrationService registrationService;


    @Autowired
    private CountryService countryService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    @Qualifier("userValidator")
    private Validator userValidator;

    @GetMapping("/login")
    public String getLoginPage(@AuthenticationPrincipal User user) {
        if (user != null) return "redirect:/films";
        return "user/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {

        if (user != null) return "redirect:/films";

        model.put("countries", countryService.getCountries());

        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            registrationService.saveUser(user);
            model.addAttribute("message", "Мы выслали вам на почту ссылку активации вашего аккаунта. Пожалуйста, перейдите по ней.");
            return "message";
        }

        model.addAttribute("countries", countryService.getCountries());

        return "user/registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(
            Model model,
            @PathVariable String code
    ) {
        if (registrationService.activateUser(code)) {
            return "redirect:/login";
        } else {
            model.addAttribute("message", "Данный код не действителен.");
        }

        return "message";
    }
}
