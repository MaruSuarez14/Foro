package com.esliceu.Forum.Repos;

import com.esliceu.Forum.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByTitle(String title);
    List<Category> findBySlug(String slug);

    @Modifying
    @Query("update Category set title = :title, description = :description  where category_id = :id")
    void updateCategory(String title, String description, long id);
}
