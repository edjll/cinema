package ru.edjll.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edjll.cinema.domain.Category;
import ru.edjll.cinema.domain.Film;
import ru.edjll.cinema.service.CategoryService;
import ru.edjll.cinema.service.FilmService;
import ru.edjll.cinema.validation.category.CategoryValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private CategoryValidator categoryValidator;

    @GetMapping("/admin/category/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getCreateCategoryPage(Map<String, Object> model) {

        model.put("films", filmService.getFilms());

        return "category/create";
    }

    @PostMapping("/admin/category/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createCategory(
            @RequestParam(name = "film[]", required = false) List<Long> filmsId,
            @Valid Category category,
            BindingResult bindingResult,
            Model model
    ) {

        List<Film> checkedFilms = filmService.getFilmsById(filmsId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            categoryService.save(category, checkedFilms);
            return "redirect:/admin/categories";
        }

        model.addAttribute("films", filmService.getFilms());
        model.addAttribute("checkedFilms", checkedFilms);

        return "category/create";
    }

    @GetMapping("/admin/category/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUpdateCategoryPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Category category = categoryService.getCategoryById(id);

        if (category == null) {
            model.put("message", "Категория с таким id не найдена");
            return "message";
        }

        model.put("category", category);
        model.put("films", filmService.getFilmsWithoutCategory(category));

        return "category/update";
    }

    @PostMapping("/admin/category/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateCategory(
            @RequestParam(name = "film[]", required = false) List<Long> filmsId,
            @Valid Category category,
            BindingResult bindingResult,
            Model model
    ) {

        List<Film> checkedFilms = filmService.getFilmsById(filmsId);

        categoryValidator.updatingCategoryValidate(checkedFilms, category.getId(), bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", controllerUtils.getErrors(bindingResult));
        } else {
            categoryService.save(category, checkedFilms);
            return "redirect:/admin/categories";
        }

        model.addAttribute("category", categoryService.getCategoryById(category.getId()));
        model.addAttribute("films", filmService.getFilmsWithoutCategory(category));

        return "category/update";
    }

    @GetMapping("/admin/category/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getDeleteCategoryPage(
            @PathVariable Long id,
            Map<String, Object> model
    ) {

        Category category = categoryService.getCategoryById(id);

        if (category == null) {
            model.put("message", "Категория с таким id не найдена");
            return "message";
        }
        if (categoryService.hasFilmsWithOneCategory(category.getId())) {
            model.put("message", "Невозможно удалить категорию, которая имеет фильмы с одной категорией");
            return "message";
        }

        model.put("category", category);

        return "category/delete";
    }

    @PostMapping("/admin/category/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCategory(@RequestParam Long id) {

        Category category = categoryService.getCategoryById(id);

        categoryService.delete(category);

        return "redirect:/admin/categories";
    }
}
