package ru.edjll.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.UserSubscribe;

@Repository
public interface UserSubscribeRepo extends JpaRepository<UserSubscribe, Long> {

}