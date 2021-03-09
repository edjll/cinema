package ru.edjll.cinema.validation.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import ru.edjll.cinema.domain.Category;
import ru.edjll.cinema.domain.Film;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.service.SubscribeService;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

@Service
public class FilmValidator {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    @Qualifier("filmPreviewValidator")
    private Validator filmPreviewValidator;

    @Autowired
    @Qualifier("creatingFilmPreviewValidator")
    private Validator creatingFilmPreviewValidator;

    @Autowired
    @Qualifier("filmTrailerValidator")
    private Validator filmTrailerValidator;

    @Autowired
    @Qualifier("creatingFilmTrailerValidator")
    private Validator creatingFilmTrailerValidator;

    @Autowired
    @Qualifier("filmVideoValidator")
    private Validator filmVideoValidator;

    public void creatingFilmValidate(
            MultipartFile preview,
            MultipartFile trailer,
            MultipartFile video,
            List<Category> categories,
            BindingResult bindingResult
    ) {
        creatingFilmPreviewValidator.validate(preview, bindingResult);
        creatingFilmTrailerValidator.validate(trailer, bindingResult);
        filmVideoValidator.validate(video, bindingResult);
        if (categories == null || categories.isEmpty()) {
            bindingResult.rejectValue("categories", "size", "error.validation.film.categories.size");
        }
    }

    public void updatingFilmValidate(
            Film film,
            MultipartFile preview,
            MultipartFile trailer,
            MultipartFile video,
            List<Category> categories,
            List<Subscribe> subscribes,
            BindingResult bindingResult
    ) {
        filmPreviewValidator.validate(preview, bindingResult);
        filmTrailerValidator.validate(trailer, bindingResult);
        filmVideoValidator.validate(video, bindingResult);
        if (categories == null || categories.isEmpty()) {
            bindingResult.rejectValue("categories", "size", "error.validation.film.categories.size");
        }
        if (subscribes != null) {
            List<Subscribe> subscribesWithOneFilm = subscribeService.getSubscribesWithOneFilmByFilm(film.getId());
            subscribesWithOneFilm.forEach(subscribe -> {
                if (!subscribes.contains(subscribe)) {
                    bindingResult.rejectValue("subscribes", "oneFilm", "error.validation.film.subscribes.oneFilm");
                }
            });
        } else if (subscribeService.getSubscribesWithOneFilmByFilm(film.getId()).size() > 0) {
            bindingResult.rejectValue("subscribes", "oneFilm", "error.validation.film.subscribes.oneFilm");
        }
    }
}
