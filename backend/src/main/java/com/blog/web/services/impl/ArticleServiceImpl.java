package com.blog.web.services.impl;

import com.blog.web.dto.ArticleDto;
import com.blog.web.dto.ArticlePublicDto;
import com.blog.web.mappers.ArticleMapper;
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

import static com.blog.web.mappers.ArticleMapper.*;

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
    public Optional<ArticleDto> findArticleById(long articleId) {
        final Optional<Article> otpArticle = articleRepository.findById(articleId);
        if(otpArticle.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(mapToArticleDto(otpArticle.get()));
        }
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        final String username = SecurityUtil.getSessionUser();
        final UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return;
        }
        final Article article = mapToArticle(articleDto);
        article.setCreatedBy(user);
        articleRepository.save(article);
    }

    @Override
    public boolean delete(long articleId) {
        final Optional<ArticleDto> optArticle = this.findArticleById(articleId);
        if(optArticle.isEmpty()) { return false; } // cant find article, give up
        final ArticleDto article = optArticle.get();
        String ownerId = article.getUsername();

        final Optional<UserEntity> optUser = userService.getLoggedInUser();
        if (optUser.isEmpty()) { return false; } // not logged in, not allowed to delete
        final UserEntity user = optUser.get();
        String userId = user.getUsername();

        if (!ownerId.equals(userId)) { return false; } // logged in a different user, not allowed to delete
        else {
            articleRepository.deleteById(articleId);
            return true;
        }
    }

    @Override
    public ArticlePublicDto findArticlePublicById(long articleId) {
        return new ArticlePublicDto(articleRepository.findById(articleId).get());
    }

    @Override
    public List<ArticlePublicDto> searchPublicArticles(String search) {
        List<Article> articles = articleRepository.searchArticles(search);
        return articles.stream().map(article -> mapToArticlePublicDto(article)).collect(Collectors.toList());
    }
}
