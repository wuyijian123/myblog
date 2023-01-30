package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Links;

import java.util.List;
import java.util.UUID;

public interface LinksService {
    List<Links> getAll();
    Messages add(Links links);
    Messages update(Links links);
    Messages remove(UUID uuid);
}
