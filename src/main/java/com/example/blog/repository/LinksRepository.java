package com.example.blog.repository;

import com.example.blog.entity.Links;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinksRepository extends JpaRepository<Links, UUID> {
    List<Links> findAllBy();
}
