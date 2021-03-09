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
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.service.CategoryService;
import ru.edjll.cinema.service.FilmService;
import ru.edjll.cinema.service.SubscribeService;
import ru.edjll.cinema.service.UserService;
import ru.edjll.cinema.validation.film.FilmValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private FilmValidator filmValidator;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping("/admin/film/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createFilmPage(Map<String, Object> model) {

        model.put("categories", categoryService.getCategories());
        model.put("accessTypes", AccessType.values());
        model.put("subscribes", subscribeService.getSubscribes());

        return "film/create";
    }

    @PostMapping("/admin/film/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createFilm(
            @Valid Film film,
            BindingResult bindingResult,
            @RequestParam AccessType accessType,
            @RequestParam(defaultValue = "0") Double price,
            @RequestParam MultipartFile previewFile,
            @RequestParam MultipartFile trailerFile,
            @RequestParam(required = false) MultipartFile videoFile,
            @RequestParam(name = "category[]", required = false) List<Long> categoriesIds,
            @RequestParam(name = "subscribe[]", required = false) List<Long> subscribesIds,
            Model model
    ) throws IOException {

        List<Category> categories = categoryService.getCategoriesById(categoriesIds);
        List<Subscribe> subscribes = subscribeService.getSubscribesById(subscribesIds);

        filmValidator.creatingFilmValidate(previewFile, trailerFile, videoFile, categories, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            filmService.save(
                    film,
                    accessType,
                    price,
                    previewFile,
                    trailerFile,
                    videoFile,
                    categories,
                    subscribes
            );

            return "redirect:/admin/films";
        }

        model.addAttribute("checkedCategories", categories);
        model.addAttribute("checkedSubscribes", subscribes);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("accessTypes", AccessType.values());
        model.addAttribute("subscribes", subscribeService.getSubscribes());

        return "film/create";
    }

    @GetMapping("/admin/film/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUpdateFilmPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            model.put("message", "Фильм с таким id не найден");
            return "message";
        }

        model.put("film", film);
        model.put("categories", categoryService.getCategories());
        model.put("accessTypes", AccessType.values());
        model.put("subscribes", subscribeService.getSubscribes());

        return "film/update";
    }

    @PostMapping("/admin/film/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateFilm(
            @Valid Film film,
            BindingResult bindingResult,
            @RequestParam AccessType accessType,
            @RequestParam(defaultValue = "0") Double price,
            @RequestParam MultipartFile previewFile,
            @RequestParam MultipartFile trailerFile,
            @RequestParam(required = false) MultipartFile videoFile,
            @RequestParam(name = "category[]", required = false) List<Long> categoriesIds,
            @RequestParam(name = "subscribe[]", required = false) List<Long> subscribesIds,
            Model model
    ) throws IOException {

        Film filmFromDB = filmService.getFilmById(film.getId());

        if (filmFromDB == null) {
            model.addAttribute("message", "Фильм с таким id не найден");
            return "message";
        }

        List<Category> categories = categoryService.getCategoriesById(categoriesIds);
        List<Subscribe> subscribes = subscribeService.getSubscribesById(subscribesIds);

        filmValidator.updatingFilmValidate(film, previewFile, trailerFile, videoFile, categories, subscribes, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            filmService.update(
                    film,
                    accessType,
                    price,
                    previewFile,
                    trailerFile,
                    videoFile,
                    categories,
                    subscribes
            );

            return "redirect:/admin/films";
        }

        model.addAttribute("film", filmFromDB);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("accessTypes", AccessType.values());
        model.addAttribute("subscribes", subscribeService.getSubscribes());

        return "film/update";
    }

    @GetMapping("/admin/film/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getDeleteFilmPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            model.put("message", "Фильм с таким id не найден");
            return "message";
        }
        if (subscribeService.getCountSubscribesWithOneFilmByFilm(film.getId()) > 0) {
            model.put("message", "Данный фильм нельзя удалить, т.к. он имеет подписки с одним фильмом");
            return "message";
        }
        if (filmService.hasUsers(film.getId())) {
            model.put("message", "Данный фильм нельзя удалить, т.к. его уже купили");
            return "message";
        }

        model.put("film", film);

        return "film/delete";
    }

    @PostMapping("/admin/film/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteFilm(
            @RequestParam Long id,
            Map<String, Object> model
    ) {

        if (filmService.getFilmById(id) == null) {
            model.put("message", "Фильм с таким id не найден");
            return "message";
        }

        filmService.delete(id);

        return "redirect:/admin/films";
    }

    @GetMapping("/films")
    public String getFilmsPage(Map<String, Object> model) {

        model.put("films", filmService.filterFilms(0));

        return "films";
    }

    @GetMapping("/films/search")
    public String searchFilm(
            @RequestParam String searchParam,
            Map<String, Object> model
    ) {

        model.put("films", filmService.getDesiredFilms(searchParam, 0));

        return "filmsByCategory";
    }

    @GetMapping("/films/category/{id}")
    public String getFilmsByCategoryPage(
            Map<String, Object> model,
            @PathVariable Long id
    ) {

        Category category = categoryService.getCategoryById(id);

        if (category == null) {
            model.put("message", "Категории с таким id не существует");
            return "message";
        }

        List<Film> films = filmService.getFilmsByCategory(category, 0);

        model.put("films", films);
        model.put("category", category);

        return "filmsByCategory";
    }

    @GetMapping("/films/subscribe/{id}")
    public String getFilmsBySubscribe(
            Map<String, Object> model,
            @PathVariable Long id
    ) {

        Subscribe subscribe = subscribeService.getById(id);

        if (subscribe == null) {
            model.put("message", "Подписки с таким id не существует");
            return "message";
        }

        List<Film> films = filmService.getFilmsBySubscribe(subscribe, 0);

        model.put("films", films);
        model.put("subscribe", subscribe);

        return "filmsByCategory";
    }

    @GetMapping("/film/{id}")
    public String getFilmPage(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            model.put("message", "Фильм с таким id не найден");
            return "message";
        }

        boolean access = film.getAccess().getType() == AccessType.FREE ||
                userService.getUserById(user.getId()).getFilms().contains(film) ||
                filmService.hasUserAccess(film.getId(), user.getId());

        model.put("film", film);
        model.put("accessTypes", AccessType.values());
        model.put("access", access);
        model.put("activeSubscribes", subscribeService.getActiveSubscribesByFilm(film.getId()));

        return "film/profile";
    }

    @GetMapping("/film/{id}/subscribes")
    public String getFilmSubscribesPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            model.put("message", "Фильм с таким id не найден");
            return "message";
        }

        model.put("film", film);
        model.put("filmSubscribes", subscribeService.getActiveSubscribesByFilm(film.getId()));

        return "film/subscribes";
    }

    @PostMapping("/film/buy")
    public String buyFilm(
            @AuthenticationPrincipal User user,
            @RequestParam Long id,
            Map<String, Object> model
    ) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            model.put("message", "Произошла ошибка, фильм с таким id не найден");
            return "message";
        }

        userService.addFilmToUser(user, film);
        securityUtils.updateAuthenticationToken();

        return "redirect:/film/" + film.getId() + "/watch";
    }

    @GetMapping("/film/{id}/watch")
    public String getWatchFilmPage(
            @AuthenticationPrincipal User authorizedUser,
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Film film = filmService.getFilmById(id);

        if (film == null) {
            model.put("message", "Фильм с таким id не найден");
            return "message";
        }
        if (film.getVideo() == null) {
            model.put("message", "Фильм ещё не вышел");
            return "message";
        }

        User user = userService.getUserById(authorizedUser.getId());

        if (user.getRoles().contains(Role.ADMIN) ||
            film.getAccess().getType() == AccessType.FREE ||
            user.getFilms().contains(film) ||
            filmService.hasUserAccess(film.getId(), user.getId())
        ) {
            model.put("film", film);
            return "film/watch";
        }

        model.put("message", "У вас нет доступа к этому фильму");

        return "message";
    }
}
