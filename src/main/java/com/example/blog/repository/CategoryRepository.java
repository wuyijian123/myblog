package com.example.blog.repository;

import com.example.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findAllBy();
    Category findCategoriesByArticlesId(UUID aId);

    Category findByIdEquals(UUID uuid);
    Category findByName(String name);

}
