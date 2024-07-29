package com.blog.web.services;

import com.blog.web.dto.ArticleDto;
import com.blog.web.dto.ArticlePublicDto;
import com.blog.web.models.Article;

import java.util.Optional;
import java.util.Set;

public interface ArticleService {
    Set<ArticlePublicDto> findAllArticles();

    Optional<Article> saveArticle(ArticleDto article);

    Optional<ArticleDto> findArticleById(long articleId);

    void updateArticle(ArticleDto articleDto);

    boolean delete(long articleId);

    ArticlePublicDto findArticlePublicById(long articleId);

    Set<ArticlePublicDto> searchPublicArticles(String search);
}
