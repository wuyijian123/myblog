package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface TagService {
    List<Tag> getAll();
    List<Tag> getAllByArticleId(UUID uuid);
    Tag getByName(String name);
    Tag getById(UUID tagId);
    Tag add(Tag tag);
    Tag update(Tag tag);
    Messages remove(UUID uuid);




}
