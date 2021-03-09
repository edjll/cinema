package ru.edjll.cinema.repository;

import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Category;
import ru.edjll.cinema.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.domain.User;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FilmRepo extends JpaRepository<Film, Long> {

    List<Film> findAllByTitleContainsOrDescriptionContainsIgnoreCase(String titlePart, String descriptionPart, Pageable pageable);

    List<Film> findAllByCategoriesContains(Category category, Pageable pageable);

    List<Film> findAllBySubscribesContains(Subscribe subscribe, Pageable pageable);

    List<Film> findAllByCategoriesNotContains(Category category);

    List<Film> findAllBySubscribesNotContains(Subscribe subscribe);

    List<Film> findAllByIdIn(Iterable<Long> ids);

    @Query("select film from Film film join film.categories category where size(film.categories) = 1 and category.id = ?1")
    List<Film> findFilmsWithOneCategory(Long categoryId);

    @Query("select " +
            "case when count(f.id) > 0 then true " +
            "else false end " +
            "from User u join u.subscribes us join us.subscribePeriod sp join sp.subscribe s join s.films f " +
            "where f.id = ?1 and u.id = ?2")
    Boolean hasUserAccess(Long filmId, Long userId);

    @Query("select " +
            "case when count(u) > 0 then true " +
            "else false end " +
            "from Film f join f.users u " +
            "where f.id = ?1")
    Boolean hasUsers(Long filmId);

    @Query("select count(s) from Film f join f.subscribes s join s.periods p where f.id = ?1 and p.active = true group by s")
    Optional<Integer> getCountActiveSubscribes(Long filmId);
}