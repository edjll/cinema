package ru.edjll.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Promo;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromoRepo extends JpaRepository<Promo, Long> {

    Promo findByName(String name);

    @Query("select size(promo.users) from Promo promo where promo.id = ?1")
    Optional<Integer> getCountActivation(Long id);

    @Query(
            value = "select 1 from promo_user where promo_id = ?1 and user_id = ?2",
            nativeQuery = true
    )
    Optional<Integer> alreadyActivate(Long promoId, Long userId);
}
