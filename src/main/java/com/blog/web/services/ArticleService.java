package com.blog.web.services;

import com.blog.web.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> findAllArticles();
}
