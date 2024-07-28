package com.blog.web.services;

import com.blog.web.dto.ArticleDto;
import com.blog.web.dto.ArticlePublicDto;
import com.blog.web.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticlePublicDto> findAllArticles();

    Optional<Article> saveArticle(ArticleDto article);

    Optional<ArticleDto> findArticleById(long articleId);

    void updateArticle(ArticleDto articleDto);

    boolean delete(long articleId);

    //List<ArticleDto> searchArticles(String search);

    ArticlePublicDto findArticlePublicById(long articleId);

    List<ArticlePublicDto> searchPublicArticles(String search);
}
