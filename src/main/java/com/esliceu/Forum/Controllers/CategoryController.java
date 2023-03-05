package com.esliceu.Forum.Controllers;

import com.esliceu.Forum.Forms.CategoryForm;
import com.esliceu.Forum.Forms.UserForm;
import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Services.CategoryService;
import com.esliceu.Forum.Services.TokenService;
import com.esliceu.Forum.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {
    TokenService tokenService;
    CategoryService categoryService;

    public CategoryController(TokenService tokenService, CategoryService categoryService){
        this.tokenService = tokenService;
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories")
    @CrossOrigin(origins = "http://localhost:3000")
    public Category createCategory(@Valid @RequestBody CategoryForm body) {
        Category category = categoryService.createCategory(body);
        if(category!=null) {
            return category;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/categories/{slug}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Category getOneCategory(@PathVariable String slug) {
        Category category = categoryService.getCategoryBySlug(slug);
        if(category!=null) {
            return category;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/categories/{slug}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Category updateCategory(@Valid @RequestBody CategoryForm body, @PathVariable String slug) {
        Category category = categoryService.getCategoryBySlug(slug);
        categoryService.update(body.getTitle(), body.getDescription(), category.getId());
        return categoryService.getCategoryBySlug(slug);
    }

    @DeleteMapping("categories/{categorySlug}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object deleteCategory(@PathVariable String categorySlug) {
        categoryService.deleteCategory(categorySlug);
        return true;
    }





}
