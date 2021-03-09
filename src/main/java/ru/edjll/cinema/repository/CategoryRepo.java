package ru.edjll.cinema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.edjll.cinema.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByName(String name);

    List<Category> findAllByIdIn(Iterable<Long> ids);

    @Query("select " +
            "case when count(f.id) > 0 then true " +
            "else false end " +
            "from Category c join c.films f " +
            "where c.id = ?1 and size(f.categories) = 1")
    Boolean hasFilmsWithOneCategory(Long categoryId);

}