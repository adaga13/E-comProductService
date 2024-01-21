package org.scaler.ecommerce.controllers;

import org.scaler.ecommerce.models.Product;
import org.scaler.ecommerce.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String[] getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{name}")
    public List<Product> getCategory(@PathVariable("name") String category) {
        return categoryService.getCategory(category);
    }
}
