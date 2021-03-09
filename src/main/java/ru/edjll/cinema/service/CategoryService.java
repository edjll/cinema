package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.edjll.cinema.domain.Category;
import ru.edjll.cinema.domain.Film;
import ru.edjll.cinema.repository.CategoryRepo;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Collection<Category> getCategories() {
        return categoryRepo.findAll(Sort.by("id").descending());
    }

    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        Category category = categoryRepo.getOne(id);
        categoryRepo.delete(category);
    }

    public void save(Category category, List<Film> films) {
        if (films != null) category.setFilms(new HashSet<>(films));
        categoryRepo.save(category);
    }

    public void delete(Category category) {
        category.getFilms().clear();
        categoryRepo.delete(category);
    }

    public List<Category> getCategoriesById(List<Long> ids) {
        if (ids == null) return null;
        return categoryRepo.findAllByIdIn(ids);
    }

    public Boolean hasFilmsWithOneCategory(Long categoryId) {
        if (categoryId == null) return false;
        return categoryRepo.hasFilmsWithOneCategory(categoryId);
    }
}
