package com.blog.web.services;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> findAllArticles();

    Article saveArticle(Article article);

    ArticleDto findArticleById(long articleId);

    void updateArticle(ArticleDto articleDto);
}
