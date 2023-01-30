package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Article;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {
    private  final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> get() {
        return articleRepository.findAllByOrderByUpdateTimeDesc();
    }

    @Override
    public List<Article> get(int page,int size) {
        return articleRepository.findAllByOrderByUpdateTimeDesc(PageRequest.of(page,size));
    }

    @Override
    public Integer count() {
        return articleRepository.countAllBy();
    }

    @Override
    public Article getById(UUID uuid) {
        return articleRepository.findById(uuid).orElse(null);
    }

    @Override
    public List<Article> getCreatorArticle(UUID uuid) {
        return articleRepository.findArticleByCreatorIdEqualsOrderByCreateTime(uuid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Messages delete(UUID aId,String token) {
        Article article=articleRepository.findById(aId).orElse(null);
        if (article==null){
            return Messages.WRONG_ID;
        }
        UUID creatorId=article.getCreator().getId();

        if (AuthUtils.invalid(creatorId,token)){
            return Messages.NO_PERMISSION;
        }
        articleRepository.delete(article);
            return Messages.SUCCESS;

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Messages delete(UUID aId) {
        Article article=articleRepository.findById(aId).orElse(null);
        if (article==null){
            return Messages.WRONG_ID;
        }
        articleRepository.delete(article);
        return Messages.SUCCESS;

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Article update(Article article, String token) {
        UUID creatorID =article.getCreator().getId();
        if(AuthUtils.invalid(creatorID,token)){
            return null;
        }
        article.setUpdateTime(new Date());
        return articleRepository.save(article);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Article add(Article article) {
        return  articleRepository.save(article);
    }

    @Override
    public List<Article> search(String kw) {
        return articleRepository.findByQuery(kw);
    }

    @Override
    public List<Article> getByCategory(String categoryName) {
        return articleRepository.findArticlesByCategoryNameEqualsOrderByCreateTimeDesc(categoryName);
    }

    @Override
    public List<Article> getByTagsId(UUID tagsId) {
        return articleRepository.findArticlesByTagsIdEqualsOrderByCreateTimeDesc(tagsId);
    }

    @Override
    public List<Article> getByTagsName(String name) {
        return articleRepository.findArticlesByTagsName(name);
    }
}
