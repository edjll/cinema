package ru.edjll.cinema.validation.film.video;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Component
public class FilmVideoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Pattern pattern = Pattern.compile("video.*");
        MultipartFile video = (MultipartFile) object;

        if (!video.isEmpty() && !pattern.matcher(video.getContentType()).matches()) {
            errors.rejectValue("video", "type", "error.validation.film.trailer.type");
        }
    }
}