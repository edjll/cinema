package ru.edjll.cinema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.SubscribePeriod;
import ru.edjll.cinema.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Collections;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationCode(String code);

    Collection<User> findAllByIdIsNot(Long id);

    @Query("select " +
            "case when count(u) > 0 then true " +
            "else false end " +
            "from User u join u.subscribes us join us.subscribePeriod sp " +
            "where u.id = ?2 and sp.subscribe.id = ?1")
    Boolean hasSubscribe(Long subscribeId, Long userId);
}
