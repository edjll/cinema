package ru.edjll.cinema.repository;

import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {

}