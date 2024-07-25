package com.blog.web.repository;

import com.blog.web.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a from Article a WHERE a.title LIKE CONCAT('%', :search, '%')")
    HashSet<Article> searchArticles(String search);
}
