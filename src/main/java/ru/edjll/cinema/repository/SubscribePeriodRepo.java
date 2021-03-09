package ru.edjll.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Subscribe;
import ru.edjll.cinema.domain.SubscribePeriod;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribePeriodRepo extends JpaRepository<SubscribePeriod, Long> {

    @Query(
            value = "select count(user_subscribe.id) " +
                    "from user_subscribe join subscribe_period " +
                    "on subscribe_period.id = ?1 " +
                    "and user_subscribe.subscribe_period_id = subscribe_period.id " +
                    "where datediff(curtime(), user_subscribe.date_start) / 1000 / 60 / 60 / 24 < subscribe_period.period " +
                    "group by user_subscribe.subscribe_period_id",
            nativeQuery = true
    )
    Optional<Integer> countByActiveSubscribers(Long subscribePeriodId);

    List<SubscribePeriod> findAllByIdIn(Iterable<Long> ids);

    @Query(
            value = "select * " +
                    "from subscribe_period " +
                    "where id in (select subscribe_id from promo_subscribe where subscribe_id in ?1)",
            nativeQuery = true
    )
    List<SubscribePeriod> findSubscribePeriodWithPromo(Iterable<Long> ids);
}
