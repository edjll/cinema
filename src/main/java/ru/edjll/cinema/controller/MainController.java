package ru.edjll.cinema.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.edjll.cinema.domain.User;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user) {

        if (user != null) return "redirect:/films";

        return "index";
    }
}
