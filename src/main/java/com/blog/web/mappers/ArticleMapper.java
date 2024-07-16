package com.blog.web.mappers;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;

public class ArticleMapper {
    public static Article mapToArticle(ArticleDto articleDto) {
        return new Article(articleDto);
    }

    public static ArticleDto mapToArticleDto(Article article) {
        return new ArticleDto(article);
    }
}
