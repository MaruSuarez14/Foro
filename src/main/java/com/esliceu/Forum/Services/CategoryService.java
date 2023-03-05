package com.esliceu.Forum.Services;

import com.esliceu.Forum.Forms.CategoryForm;
import com.esliceu.Forum.Model.Category;
import com.esliceu.Forum.Repos.CategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryBySlug(String slug) {
        List<Category> categories =  categoryRepo.findBySlug(slug);
        if(categories.isEmpty()){
            return null;
        } else {
            Category category = categories.get(0);
            category.set_id(category.getId());
            return category;
        }
    }

    public Category createCategory(CategoryForm categoryForm) {
        Category category = new Category();
        category.setTitle(categoryForm.getTitle());
        category.setDescription(categoryForm.getDescription());
        category.setColor(createColor());
        category.setSlug(createSlug(categoryForm.getTitle()));
        long id = categoryRepo.save(category).getId();
        category.set_id(id);
        return category;
    }

    public String createColor() {
        int number = new Random().nextInt(256);
        return "hsl(" + number + ", 50%, 50%)";
    }
    public String createSlug (String title) {
        String slug = title.replaceAll("[^a-zA-Z0-9 ]", "")
                .replaceAll("\\s", "-")
                .toLowerCase();
        List<Category> categories = categoryRepo.findByTitle(title);
        int number = (int) categories.stream().filter(s -> s.getTitle().equals(title)).count();
        if( number > 0 ) {
            slug = slug + "-" + number;
        }
        return slug;
    }

    @Transactional
    public void update (String title, String description, long id) {
        categoryRepo.updateCategory(title, description, id);
    }
}
