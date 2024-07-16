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

import static com.blog.web.mappers.ArticleMapper.mapToArticle;
import static com.blog.web.mappers.ArticleMapper.mapToArticleDto;

@Service
public class ArticleServiceImpl implements ArticleService {
    final private ArticleRepository articleRepository;
    final private UserRepository userRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }


    @Override
    public List<ArticleDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map((article) -> mapToArticleDto(article)).collect(Collectors.toList());
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
}
