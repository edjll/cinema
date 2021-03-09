package ru.edjll.cinema.validation.film.preview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CreatingFilmPreviewValidator implements Validator {

    @Autowired
    private FilmPreviewValidator filmPreviewValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        MultipartFile preview = (MultipartFile) object;

        if (preview.isEmpty()) {
            errors.rejectValue("preview", "null", "error.validation.film.preview.null");
        } else {
            filmPreviewValidator.validate(object, errors);
        }
    }
}