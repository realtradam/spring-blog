package com.blog.web.mappers;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;

public class ArticleMapper {
    public static Article mapToArticle(ArticleDto articleDto) {
        Article article = Article.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .photoUrl(articleDto.getPhotoUrl())
                .content(articleDto.getContent())
                .createdBy(articleDto.getCreatedBy())
                .createdOn(articleDto.getCreatedOn())
                .updatedOn(articleDto.getUpdatedOn())
                .build();
        return article;
    }

    public static ArticleDto mapToArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .photoUrl(article.getPhotoUrl())
                .content(article.getContent())
                .createdBy(article.getCreatedBy())
                .createdOn(article.getCreatedOn())
                .updatedOn(article.getUpdatedOn())
                .build();
    }
}
