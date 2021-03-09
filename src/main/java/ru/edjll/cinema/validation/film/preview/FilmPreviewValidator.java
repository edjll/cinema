package ru.edjll.cinema.validation.film.preview;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;


@Component
public class FilmPreviewValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Pattern pattern = Pattern.compile("image.*");
        MultipartFile preview = (MultipartFile) object;

        if (!preview.isEmpty() && !pattern.matcher(preview.getContentType()).matches()) {
            errors.rejectValue("preview", "type", "error.validation.film.preview.type");
        }
    }
}
