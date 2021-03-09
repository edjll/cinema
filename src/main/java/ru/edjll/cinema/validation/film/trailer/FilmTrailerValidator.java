package ru.edjll.cinema.validation.film.trailer;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Component
public class FilmTrailerValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Pattern pattern = Pattern.compile("video.*");
        MultipartFile trailer = (MultipartFile) object;

        if (!trailer.isEmpty() && !pattern.matcher(trailer.getContentType()).matches()) {
            errors.rejectValue("trailer", "type", "error.validation.film.trailer.type");
        }
    }
}
