package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Article;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface ArticleService {
    List<Article> get();
    List<Article> get(int page,int size);
    Integer count();
    Article getById(UUID uuid);
    List<Article> getCreatorArticle(UUID uuid);
    Messages delete(UUID aId,String token);
    Messages delete(UUID aId);

    Article update(Article article,String token);
    Article add(Article article);
    List<Article> search(String kw);
    List<Article> getByCategory(String categoryName);
    List<Article> getByTagsId(UUID tagsId);

    List<Article> getByTagsName(String name);




}
