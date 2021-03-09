package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.service.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PromoService promoService;

    @GetMapping("/films")
    public String getFilmsPage(Map<String, Object> model) {
        List<Film> films = filmService.getFilms();
        model.put(
            "films",
            films.stream().collect(Collectors.toMap(
                    film -> film,
                    film -> subscribeService.getSubscribesWithOneFilmByFilm(film.getId())
            ))
        );
        model.put(
            "filmHasUsers",
            films.stream().collect(
                Collectors.toMap(
                    Film::getId,
                    film -> filmService.hasUsers(film.getId())
                )
            ));
        return "admin/films";
    }

    @GetMapping("/categories")
    public String getCategoriesPage(Map<String, Object> model) {
        Map<Category, List<Film>> categories = categoryService.getCategories().stream().collect(Collectors.toMap(
                category -> category,
                category -> filmService.getFilmsWithOneCategory(category.getId())
        ));
        model.put("categories", categories);
        return "admin/categories";
    }

    @GetMapping("/subscribes")
    public String getSubscribesPage(Map<String, Object> model) {
        Map<Subscribe, Integer> subscribes = subscribeService.getSubscribes().stream().collect(Collectors.toMap(
                subscribe -> subscribe,
                subscribe -> subscribeService.getActiveSubscribersCount(subscribe.getId())
        ));
        model.put("subscribes", subscribes);
        return "admin/subscribes";
    }

    @GetMapping("/users")
    public String getUsersPage(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {
        model.put("users", userService.getUsersWithoutUser(user.getId()));
        model.put("roleAdmin", Role.ADMIN);
        return "admin/users";
    }

    @GetMapping("/promo")
    public String getPromoPage(
            Map<String, Object> model
    ) {
        Map<Promo, Integer> promos = promoService.getPromos().stream().collect(Collectors.toMap(
                promo -> promo,
                promo -> promoService.getCountActivation(promo.getId())
        ));
        model.put("promos", promos);
        return "admin/promo";
    }
}
