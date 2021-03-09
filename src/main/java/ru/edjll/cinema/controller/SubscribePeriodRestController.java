package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.cinema.domain.SubscribePeriod;
import ru.edjll.cinema.service.SubscribePeriodService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SubscribePeriodRestController {

    @Autowired
    private SubscribePeriodService subscribePeriodService;

    @GetMapping("/subscribe/period/{id}")
    public ResponseEntity<Map<String, Object>> getPeriod(@PathVariable Long id) {

        SubscribePeriod subscribePeriod = subscribePeriodService.getById(id);

        if (subscribePeriod == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Периода подписки с таким id не существует"));
        }

        Map<String, Object> model = new HashMap<>();

        model.put("period", subscribePeriod.getPeriod());
        model.put("price", subscribePeriod.getPrice());

        return ResponseEntity.ok().body(model);
    }
}
