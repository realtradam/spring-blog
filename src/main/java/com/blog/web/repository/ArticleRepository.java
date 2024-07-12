package com.blog.web.repository;

import com.blog.web.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByTitle(String url);
    @Query("SELECT a from Article a WHERE a.title LIKE CONCAT('%', :search, '%')")
    List<Article> searchArticles(String search);
}
