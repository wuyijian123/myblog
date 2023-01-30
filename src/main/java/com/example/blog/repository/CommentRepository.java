package com.example.blog.repository;

import com.example.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByOrderByCreateTimeDesc();
    List<Comment> findCommentsByArticleIdOrderByCreateTimeDesc(UUID uuid);
    Integer countAllByArticleId(UUID uuid);
    List<Comment> findCommentsByUserId(UUID uuid);

}
