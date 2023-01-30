package com.example.blog.repository;

import com.example.blog.entity.Article;

import com.example.blog.entity.Category;
import com.example.blog.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {


   List<Article> findAllByOpenOrderByUpdateTimeDesc(boolean open);

    List<Article> findAllByOrderByUpdateTimeDesc();

    Integer countAllBy();


    List<Article> findArticleByCreatorIdEqualsOrderByCreateTime(UUID id);

    List<Article> findAllByOrderByUpdateTimeDesc(Pageable pageable);
    @Query("select a from Article a where a.title like CONCAT('%',:kw,'%') or a.content " +
            "like CONCAT('%',:kw,'%') ")
    List<Article> findByQuery(@Param("kw") String kw);

    List<Article> findArticlesByCategoryNameEqualsOrderByCreateTimeDesc(String name);

    List<Article> findArticlesByTagsIdEqualsOrderByCreateTimeDesc(UUID tagId);
    List<Article> findArticlesByTagsName(String name);



}
