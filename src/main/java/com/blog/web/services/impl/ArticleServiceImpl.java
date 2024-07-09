package com.blog.web.services.impl;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;
import com.blog.web.repository.ArticleRepository;
import com.blog.web.services.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    public ArticleServiceImpl(com.blog.web.repository.ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    private ArticleRepository articleRepository;

    @Override
    public List<ArticleDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapToArticleDto).collect(Collectors.toList());
    }

    private ArticleDto mapToArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .photoUrl(article.getPhotoUrl())
                .content(article.getContent())
                .createdOn(article.getCreatedOn())
                .updatedOn(article.getUpdatedOn())
                .build();
    }
}
