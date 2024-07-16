package com.blog.web.mappers;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;

public class ArticleMapper {
    public static Article mapToArticle(ArticleDto articleDto) {
        Article article = new Article(
                articleDto.getId(),
                articleDto.getTitle(),
                articleDto.getPhotoUrl(),
                articleDto.getContent(),
                articleDto.getCreatedBy(),
                articleDto.getCreatedOn(),
                articleDto.getUpdatedOn()
                );
        return article;
    }

    public static ArticleDto mapToArticleDto(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getPhotoUrl(),
                article.getContent(),
                article.getCreatedBy(),
                article.getCreatedOn(),
                article.getUpdatedOn()
                );
    }
}
