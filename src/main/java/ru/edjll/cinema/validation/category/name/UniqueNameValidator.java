package ru.edjll.cinema.validation.category.name;

import org.springframework.beans.factory.annotation.Autowired;
import ru.edjll.cinema.domain.Category;
import ru.edjll.cinema.service.CategoryService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, Category> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext ctx) {
        Category dbCategory = categoryService.getCategoryByName(category.getName());
        if (dbCategory == null || dbCategory.getId().equals(category.getId())) return true;

        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate("error.validation.category.name.unique")
                .addPropertyNode("name")
                .addConstraintViolation();
        return false;
    }
}