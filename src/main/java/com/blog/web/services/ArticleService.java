package com.blog.web.services;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticleDto> findAllArticles();

    Optional<Article> saveArticle(ArticleDto article);

    ArticleDto findArticleById(long articleId);

    void updateArticle(ArticleDto articleDto);

    boolean delete(Long articleId);

    List<ArticleDto> searchArticles(String search);
}
