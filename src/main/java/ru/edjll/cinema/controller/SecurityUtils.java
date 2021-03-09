package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.service.UserService;

@Component
public class SecurityUtils {

    @Autowired
    private UserService userService;

    public void updateAuthenticationToken() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User authorizedUser = (User) auth.getPrincipal();
        User userFromDB = userService.getUserById(authorizedUser.getId());

        Authentication newAuth = new UsernamePasswordAuthenticationToken(userFromDB, auth.getCredentials(), auth.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
