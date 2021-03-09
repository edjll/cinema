package ru.edjll.cinema.service;

import ru.edjll.cinema.domain.*;
import ru.edjll.cinema.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Collection<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void addFilmToUser(User user, Film film) {
        User userFromDB = getUserById(user.getId());
        userFromDB.getFilms().add(film);
        userRepo.save(userFromDB);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public Collection<User> getUsersWithoutUser(Long id) {
        return userRepo.findAllByIdIsNot(id);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(User user) {
        User userFromDB = getUserById(user.getId());
        userFromDB.setUsername(user.getUsername());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setCountry(user.getCountry());
        userFromDB.setBirthday(user.getBirthday());
        saveUser(userFromDB);
    }

    public Boolean hasSubscribe(Long subscribeId, Long userId) {
        return userRepo.hasSubscribe(subscribeId, userId);
    }

    public void updateUserRoles(String admin, User user) {
        if (admin == null && user.getRoles().contains(Role.ADMIN)) {
            user.getRoles().remove(Role.ADMIN);
            saveUser(user);
        }
        if (admin != null && !user.getRoles().contains(Role.ADMIN)) {
            user.getRoles().add(Role.ADMIN);
            saveUser(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
