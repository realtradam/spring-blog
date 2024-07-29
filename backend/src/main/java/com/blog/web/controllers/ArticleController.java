package com.blog.web.controllers;

import com.blog.web.dto.ArticleDto;
import com.blog.web.dto.ArticlePublicDto;
import com.blog.web.models.Article;
import com.blog.web.models.UserEntity;
import com.blog.web.services.ArticleService;
import com.blog.web.services.UserService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/get")
    public Article getMethod() {
        return new Article(
                5L,
                "blah",
                "blah",
                "blah",
                new UserEntity(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @GetMapping("/articles")
    public Set<ArticlePublicDto> listArticles(@RequestParam(value = "search", required = false) Optional<String> search) {
        final Set<ArticlePublicDto> articles;
        if (search.isPresent()) {
            articles = articleService.searchPublicArticles(search.get());
        } else {
            articles = articleService.findAllArticles();
        }
        return articles;
    }

    @GetMapping("/article/{articleId}")
    public ArticlePublicDto showArticle(@PathVariable("articleId") long articleId, Model model) {
        ArticlePublicDto articlePublicDto = articleService.findArticlePublicById(articleId);
        return articlePublicDto;
    }

    @PostMapping("/article/new")
    public String saveArticle(@Valid @ModelAttribute("article") ArticleDto articleDto, BindingResult result) {
        // if non-authenticated in user tries to create an article
        // redirect them to login page
        Optional<UserEntity> user = userService.getLoggedInUser();
        if (user.isEmpty()) {
            return "redirect:/login";
        } else if (!result.hasErrors()) {
            articleService.saveArticle(articleDto);
            return "redirect:/";
        }
        return "";
    }

    @GetMapping("/articles/delete/{articleId}")
    public String deleteArticle(@PathVariable("articleId") Long articleId) {
        articleService.delete(articleId);
        return "";
    }

    @PostMapping("/articles/edit/{articleId}")
    public String updateArticle(@PathVariable("articleId") long articleId, @Valid @ModelAttribute("article") ArticleDto article, BindingResult result) {
        if (result.hasErrors()) {
            return "articles/edit";
        }
        article.setId(articleId);
        articleService.updateArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/")
    public String getArticles() {
        return "redirect:/articles";
    }
}
