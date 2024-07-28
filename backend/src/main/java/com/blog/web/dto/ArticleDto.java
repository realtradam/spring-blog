package com.blog.web.dto;

import com.blog.web.models.Article;
import com.blog.web.models.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

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
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    public ArticleDto(Long id, String title, String photoUrl, String content, UserEntity createdBy, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.id = id;
        this.title = title;
        this.photoUrl = photoUrl;
        this.content = content;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public ArticleDto() {
    }

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.photoUrl = article.getPhotoUrl();
        this.content = article.getContent();
        this.createdBy = article.getCreatedBy();
        this.createdOn = article.getCreatedOn();
        this.updatedOn = article.getUpdatedOn();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Article title should not be empty") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Article title should not be empty") String title) {
        this.title = title;
    }

    public @NotEmpty(message = "Article Photo URL should not be empty") @URL(message = "Article Photo URL should be a URL") String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(@NotEmpty(message = "Article Photo URL should not be empty") @URL(message = "Article Photo URL should be a URL") String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public @NotEmpty(message = "Article Content should not be empty") String getContent() {
        return content;
    }

    public void setContent(@NotEmpty(message = "Article Content should not be empty") String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public String getUsername() {
        return createdBy.getUsername();
    }

    public Long getUserId() {
        return createdBy.getId();
    }
}
