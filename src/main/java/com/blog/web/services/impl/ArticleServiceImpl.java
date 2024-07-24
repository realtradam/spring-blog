package com.blog.web.services.impl;

import com.blog.web.dto.ArticleDto;
import com.blog.web.dto.ArticlePublicDto;
import com.blog.web.models.Article;
import com.blog.web.models.UserEntity;
import com.blog.web.repository.ArticleRepository;
import com.blog.web.repository.UserRepository;
import com.blog.web.security.SecurityUtil;
import com.blog.web.services.ArticleService;
import com.blog.web.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.blog.web.mappers.ArticleMapper;

import static com.blog.web.mappers.ArticleMapper.mapToArticle;
import static com.blog.web.mappers.ArticleMapper.mapToArticleDto;

@Service
public class ArticleServiceImpl implements ArticleService {
    final private ArticleRepository articleRepository;
    final private UserRepository userRepository;
    final private UserService userService;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.userService = userService;
    }


    @Override
    public List<ArticlePublicDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleMapper::mapToArticlePublicDto).collect(Collectors.toList());
    }

    @Override
    public Optional<Article> saveArticle(ArticleDto articleDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        Article article = mapToArticle(articleDto);
        article.setCreatedBy(user);
        return Optional.of(articleRepository.save(article));
    }

    @Override
    public ArticleDto findArticleById(long articleId) {
        Article article = articleRepository.findById(articleId).get();
        return mapToArticleDto(article);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return;
        }
        Article article = mapToArticle(articleDto);
        article.setCreatedBy(user);
        articleRepository.save(article);
    }

    @Override
    public boolean delete(Long articleId) {
        final UserEntity user = userService.getLoggedInUser().orElse(null);
        if (user == null) {
            return false;
        }
        String userId = user.getUsername();
        ArticleDto article = this.findArticleById(articleId);
        String ownerId = article.getUsername();
        if (ownerId.equals(userId)) {
            articleRepository.deleteById(articleId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ArticleDto> searchArticles(String search) {
        List<Article> articles = articleRepository.searchArticles(search);
        return articles.stream().map(article -> mapToArticleDto(article)).collect(Collectors.toList());
    }
}
