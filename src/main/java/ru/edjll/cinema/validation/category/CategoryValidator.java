package ru.edjll.cinema.validation.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import ru.edjll.cinema.domain.Film;
import ru.edjll.cinema.service.FilmService;

import java.util.List;

@Service
public class CategoryValidator {

    @Autowired
    private FilmService filmService;

    public void updatingCategoryValidate(List<Film> checkedFilms, Long categoryId, Errors errors) {
        List<Film> filmsWithOneCategory = filmService.getFilmsWithOneCategory(categoryId);
        if (checkedFilms == null && filmsWithOneCategory != null) {
            errors.rejectValue("films", "oneCategory", "error.validation.category.films.oneCategory");
        } else {
            for (int i = 0; i < filmsWithOneCategory.size(); i++) {
                if (!checkedFilms.contains(filmsWithOneCategory.get(i))) {
                    errors.rejectValue("films", "oneCategory", "error.validation.category.films.oneCategory");
                }
            }
        }
    }
}
