package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.cinema.domain.Role;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.domain.UserSubscribe;
import ru.edjll.cinema.service.UserService;
import ru.edjll.cinema.service.UserSubscribeService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSubscribeService userSubscribeService;

    @PostMapping("/admin/user/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> updateUser(
            @RequestParam Long id,
            @RequestParam(required = false) String admin
    ) {

        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Пользователя с таким id не существует"));
        }

        userService.updateUserRoles(admin, user);

        Map<String, Object> model = new HashMap<>();

        model.put("id", user.getId());
        model.put("roles", user.getRoles());

        return ResponseEntity.ok().body(model);
    }

    @PostMapping("/profile/subscribe/renewal")
    public ResponseEntity<Map<String, Object>> changeRenewal(
            @RequestParam Long userSubscribeId,
            @RequestParam boolean renewal
    ) {

        UserSubscribe userSubscribe = userSubscribeService.getById(userSubscribeId);

        if (userSubscribe == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Неверно указан id"));
        }

        userSubscribeService.changeRenewal(userSubscribe, renewal);

        return ResponseEntity.ok().body(Collections.singletonMap("renewal", userSubscribe.getRenewal()));
    }
}
