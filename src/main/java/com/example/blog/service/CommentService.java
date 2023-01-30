package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<Comment> get();
    Comment getById(UUID commentId);
    List<Comment> getByUserId(UUID userId);
    List<Comment> getByArticleId(UUID aId);
    Messages remove(UUID commentId);
    Messages remove(UUID commentId,String token);
    Comment add(Comment comment);
}
