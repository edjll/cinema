package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edjll.cinema.domain.Role;
import ru.edjll.cinema.domain.User;
import ru.edjll.cinema.repository.UserRepo;

import java.util.Collections;
import java.util.UUID;

@Service
public class RegistrationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;

    public void saveUser(User user) {

        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());

        String message = String.format(
                "Hello, %s! \nWelcome to Cinema. Please, visit next link: http://localhost:8080/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Новая акция специально для вас", "Чёрная пятница", "В честь международной акции чёрная пятница, мы решили сделать скидки на множество товаров, подробнее на нашем сайте");

        userRepo.save(user);
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) return false;

        user.setActivationCode(null);
        user.setActive(true);

        userRepo.save(user);

        return true;
    }
}
