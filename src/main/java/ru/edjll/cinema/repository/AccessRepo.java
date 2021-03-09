package ru.edjll.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Access;

@Repository
public interface AccessRepo extends JpaRepository<Access, Long> {

}