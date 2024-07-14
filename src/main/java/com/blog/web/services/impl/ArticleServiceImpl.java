package com.blog.web.services.impl;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;
import com.blog.web.models.UserEntity;
import com.blog.web.repository.ArticleRepository;
import com.blog.web.repository.UserRepository;
import com.blog.web.security.SecurityUtil;
import com.blog.web.services.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    public ArticleServiceImpl(com.blog.web.repository.ArticleRepository articleRepository, com.blog.web.repository.UserRepository userRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    @Override
    public List<ArticleDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapToArticleDto).collect(Collectors.toList());
    }

    @Override
    public Article saveArticle(ArticleDto articleDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Article article = mapToArticle(articleDto);
        article.setCreatedBy(user);
        return articleRepository.save(article);
    }

    @Override
    public ArticleDto findArticleById(long articleId) {
        Article article = articleRepository.findById(articleId).get();
        return mapToArticleDto(article);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Article article = mapToArticle(articleDto);
        article.setCreatedBy(user);
        articleRepository.save(article);
    }

    @Override
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public List<ArticleDto> searchArticles(String search) {
        List<Article> articles = articleRepository.searchArticles(search);
        return articles.stream().map(article -> mapToArticleDto(article)).collect(Collectors.toList());
    }

    private Article mapToArticle(ArticleDto articleDto) {
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

    private ArticleDto mapToArticleDto(Article article) {
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
