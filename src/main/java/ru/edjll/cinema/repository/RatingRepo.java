package ru.edjll.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Film;
import ru.edjll.cinema.domain.Rating;
import ru.edjll.cinema.domain.User;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {

    Rating findByFilmAndUser(Film film, User user);

    @Query(value = "select round(sum(value) / count(id), 2) from rating group by film_id having film_id = ?1", nativeQuery = true)
    Double getAverageRating(Long id);
}
