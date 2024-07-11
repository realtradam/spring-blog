package com.blog.web.controllers;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;
import com.blog.web.services.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String listArticles(Model model) {
        List<ArticleDto> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/articles/new")
    public String createArticleForm(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "articles/new";
    }

    @PostMapping("/articles/new")
    public String saveArticle(@ModelAttribute("article") Article article) {
        articleService.saveArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/edit/{articleId}")
    public String editArticleForm(@PathVariable("articleId") long articleId, Model model) {
        ArticleDto article = articleService.findArticleById(articleId);
        model.addAttribute("article", article);
        return "articles/edit";
    }

    @PostMapping("/articles/edit/{articleId}")
    public String updateArticle(@PathVariable("articleId") Long articleId, @ModelAttribute("article") ArticleDto article) {
        article.setId(articleId);
        articleService.updateArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles")
    public String getArticles() {
        return "redirect:/";
    }
}
