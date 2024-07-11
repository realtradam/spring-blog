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

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public ArticleDto findArticleById(long articleId) {
        Article article = articleRepository.findById(articleId).get();
        return mapToArticleDto(article);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        Article article = mapToArticle(articleDto);
    }

    private Article mapToArticle(ArticleDto articleDto) {
        Article article = Article.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .photoUrl(articleDto.getPhotoUrl())
                .content(articleDto.getContent())
                .createdOn(articleDto.getCreatedOn())
                .updatedOn(articleDto.getUpdatedOn())
                .build();
        return article;
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
