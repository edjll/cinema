package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.service.CategoryService;
import ru.edjll.cinema.service.FilmService;
import ru.edjll.cinema.service.RatingService;
import ru.edjll.cinema.service.SubscribeService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class FilmRestController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/films/category/{id}/{page}")
    public Collection<Object> getFilmsByCategory(
            @PathVariable Long id,
            @PathVariable int page
    ) {
        Category category = categoryService.getCategoryById(id);
        return filmService.convertFilm(filmService.getFilmsByCategory(category, page));
    }

    @GetMapping("/films/subscribe/{id}/{page}")
    public Collection<Object> getFilmsBySubscribe(
            @PathVariable Long id,
            @PathVariable int page
    ) {
        Subscribe subscribe = subscribeService.getById(id);
        return filmService.convertFilm(filmService.getFilmsBySubscribe(subscribe, page));
    }

    @GetMapping("/films/search/{page}")
    public Collection<Object> getDesiredFilms(
            @PathVariable int page,
            @RequestParam String searchParam
    ) {
        return filmService.convertFilm(filmService.getDesiredFilms(searchParam, page));
    }

    @GetMapping("/film/{id}/buy")
    public ResponseEntity<Map<String, Object>> getFilm(@PathVariable Long id) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Фильм с таким id не найден"));
        }

        Map<String, Object> model = new HashMap<>();

        model.put("title", film.getTitle());
        model.put("categories", film.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet()));
        model.put("price", film.getAccess().getPrice());
        model.put("id", film.getId());


        return ResponseEntity.ok().body(model);
    }

    @PostMapping("/film/{id}/rating/update")
    public ResponseEntity<Map<String, Object>> updateFilmRating(
            @PathVariable Long id,
            @RequestParam Integer value,
            @AuthenticationPrincipal User user
    ) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Фильм с таким id не найден"));
        }

        Rating rating = ratingService.getRatingByFilmAndUser(film, user);

        filmService.saveRating(film, rating, value, user);

        Map<String, Object> model = new HashMap<>();

        model.put("filmRating", ratingService.getAverageRating(film));
        model.put("userRating", value);

        return ResponseEntity.ok().body(model);
    }
}
