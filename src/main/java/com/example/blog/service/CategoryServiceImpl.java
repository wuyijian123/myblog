package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getById(UUID uuid) {
        return categoryRepository.findByIdEquals(uuid);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAllBy();
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Category add(Category category, String token) {
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})) {
            return null;
        }
        return categoryRepository.save(category);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Category update(Category category, String token) {
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})) {
            return null;

        }
        return  categoryRepository.save(category);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Messages remove(UUID cId, String token) {
        Category category = categoryRepository.findById(cId).orElse(null);
        if (category == null) {
            return Messages.WRONG_ID;
        }
        if (AuthUtils.illegalUser(token, new int[]{User.TYPE_ADMIN})) {
            return Messages.NO_PERMISSION;
        }
        categoryRepository.delete(category);
        return Messages.SUCCESS;

    }
}
