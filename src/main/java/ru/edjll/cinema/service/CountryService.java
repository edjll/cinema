package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.cinema.domain.Country;
import ru.edjll.cinema.repository.CountryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepo countryRepo;

    public List<Country> getCountries() {
        return countryRepo.findAll();
    }
}
