package com.example.blog.service;

import antlr.Token;
import com.example.blog.constant.Messages;
import com.example.blog.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category getById(UUID uuid);
    List<Category> getAll();
    Category getByName(String name);
    Category add(Category category,String token);
    Category update(Category category,String token);
    Messages remove(UUID cId,String token);
}
