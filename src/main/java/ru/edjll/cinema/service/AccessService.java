package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edjll.cinema.domain.Access;
import ru.edjll.cinema.domain.AccessType;
import ru.edjll.cinema.domain.Category;
import ru.edjll.cinema.repository.AccessRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccessService {

    @Autowired
    private AccessRepo accessRepo;

    public Access save(double price, AccessType accessType) {
        Access access;
        if (accessType == AccessType.FREE) access = new Access(0, accessType);
        else access = new Access(price, accessType);
        return accessRepo.save(access);
    }
}
