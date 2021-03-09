package ru.edjll.cinema.validation.film.trailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CreatingFilmTrailerValidator implements Validator {

    @Autowired
    private FilmTrailerValidator filmTrailerValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        MultipartFile trailer = (MultipartFile) object;

        if (trailer.isEmpty()) {
            errors.rejectValue("trailer", "type", "error.validation.film.trailer.null");
        } else {
            filmTrailerValidator.validate(object, errors);
        }
    }
}