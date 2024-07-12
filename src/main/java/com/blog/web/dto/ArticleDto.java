package com.blog.web.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Article title should not be empty")
    private String title;
    @NotEmpty(message = "Article Photo URL should not be empty")
    @URL(message = "Article Photo URL should be a URL")
    private String photoUrl;
    @NotEmpty(message = "Article Content should not be empty")
    private String content;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
