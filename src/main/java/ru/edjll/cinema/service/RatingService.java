package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edjll.cinema.domain.Film;
import ru.edjll.cinema.domain.Rating;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.repository.RatingRepo;

@Service
public class RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    public Rating save(Rating rating) {
        return ratingRepo.save(rating);
    }

    public Rating getRatingByFilmAndUser(Film film, User user) {
        return ratingRepo.findByFilmAndUser(film, user);
    }

    public Double getAverageRating(Film film) {
        return ratingRepo.getAverageRating(film.getId());
    }
}
