package ru.edjll.cinema.repository;

import org.hibernate.mapping.Subclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.domain.SubscribePeriod;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscribeRepo extends JpaRepository<Subscribe, Long> {

    @Query(
            value = "select count(user_subscribe.id) " +
                    "from user_subscribe join (select subscribe_period.id, subscribe_period.period " +
                                            "from subscribe_period join subscribe " +
                                            "on subscribe.id = ?1 " +
                                            "and subscribe_period.subscribe_id = subscribe.id) as s " +
                    "on user_subscribe.subscribe_period_id = s.id " +
                    "where datediff(curtime(), user_subscribe.date_start) / 1000 / 60 / 60 / 24 < s.period " +
                    "group by user_subscribe.subscribe_period_id",
            nativeQuery = true
    )
    Optional<Integer> getActiveSubscribesCount(Long id);

    List<Subscribe> findAllByIdIn(Iterable<Long> ids);

    Subscribe findByName(String name);

    @Query(
            value = "select id, name " +
                    "from subscribe join (select count(film_id) as count, fs1.subscribe_id " +
                                            "from film_subscribe fs1 join (select subscribe_id " +
                                                                        "from film_subscribe fs2 " +
                                                                        "where fs2.film_id = ?1) as fs2 " +
                                            "on fs1.subscribe_id = fs2.subscribe_id " +
                                            "group by fs1.subscribe_id) as fs1 " +
                    "on id = fs1.subscribe_id " +
                    "and count = 1",
            nativeQuery = true
    )
    List<Subscribe> findSubscribesWithOneFilmByFilm(Long filmId);

    @Query(
            value = "select count(film_subscribe.subscribe_id) " +
                    "from film_subscribe join (select count(film_id) as count, fs1.subscribe_id " +
                                            "from film_subscribe fs1 join (select subscribe_id " +
                                                                        "from film_subscribe fs2 " +
                                                                        "where fs2.film_id = ?1) as fs2 " +
                                            "on fs1.subscribe_id = fs2.subscribe_id " +
                                            "group by fs1.subscribe_id) as fs1 " +
                    "on film_subscribe.subscribe_id = fs1.subscribe_id " +
                    "and count = 1 " +
                    "group by film_subscribe.subscribe_id",
            nativeQuery = true
    )
    Optional<Integer> getCountSubscribesWithOneFilmByFilm(Long filmId);

    @Query("select s from Subscribe s join s.periods p where p.active = true group by s")
    List<Subscribe> findAllWithActivePeriods();

    @Query("select s2 " +
            "from Subscribe s2 " +
            "where s2.id not in (select s1.subscribe from SubscribePeriod s1 where s1.id in ?1) " +
            "and s2.id in (select sp.subscribe from SubscribePeriod sp where sp.active = true)")
    List<Subscribe> findAllWithActivePeriodsAndWithoutSubscribePeriods(Iterable<Long> ids);

    @Query("select s from Subscribe s join s.films f join s.periods sp where sp.active = true and f.id = ?1 group by s")
    List<Subscribe> findActiveSubscribesByFilm(Long filmId);
}
