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
import java.util.HashSet;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    private ArticleService articleService;
    private UserService userService;

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
    public HashSet<ArticlePublicDto> listArticles() {
        HashSet<ArticlePublicDto> articles = new HashSet<ArticlePublicDto>(articleService.findAllArticles());
        return articles;
    }

    @GetMapping("/article/{articleId}")
    public ArticlePublicDto showArticle(@PathVariable("articleId") long articleId, Model model) {
        ArticlePublicDto articlePublicDto = articleService.findArticlePublicById(articleId);
        //model.addAttribute("article", articlePublicDto);
        //UserEntity user = userService.getLoggedInUser().orElse(new UserEntity());
        //model.addAttribute("user", user);
        //return "articles/show";
        return articlePublicDto;
    }

    /*@GetMapping("/articles/new")
    public String createArticleForm(Model model) {
        model.addAttribute("user", userService.getLoggedInUser().orElse(new UserEntity()));
        model.addAttribute("article", new Article());
        return "articles/new";
    }*/

    @PostMapping("/article/new")
    public String saveArticle(@Valid @ModelAttribute("article") ArticleDto articleDto, BindingResult result) {
        // if non-authenticated in user tries to create an article
        // redirect them to login page
        UserEntity user = userService.getLoggedInUser().orElse(null);
        if (user == null) {
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
        return "redirect:/articles";
    }

    @GetMapping("/articles/edit/{articleId}")
    public String editArticleForm(@PathVariable("articleId") long articleId, Model model) {
        UserEntity user = userService.getLoggedInUser().orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
            ArticleDto articleDto = articleService.findArticleById(articleId);
            model.addAttribute("article", articleDto);
        }
        return "articles/edit";
    }

    @PostMapping("/articles/edit/{articleId}")
    public String updateArticle(@PathVariable("articleId") Long articleId, @Valid @ModelAttribute("article") ArticleDto article, BindingResult result) {
        if (result.hasErrors()) {
            return "articles/edit";
        }
        article.setId(articleId);
        articleService.updateArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/search")
    public String searchArticle(@RequestParam(value = "search") String search, Model model) {
        //UserEntity user = userService.getLoggedInUser().orElse(new UserEntity());
        //model.addAttribute("user", user);
        HashSet<ArticlePublicDto> articles = articleService.searchPublicArticles(search);
        //model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/")
    public String getArticles() {
        return "redirect:/articles";
    }
}
