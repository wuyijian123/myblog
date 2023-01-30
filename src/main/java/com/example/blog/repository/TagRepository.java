package com.example.blog.repository;

import com.example.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findByArticlesId(UUID uuid);
    List<Tag> findTagsByArticlesId(UUID uuid);
    Tag findByName(String name);
}
