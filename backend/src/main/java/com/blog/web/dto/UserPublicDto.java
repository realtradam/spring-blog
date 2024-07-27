package com.blog.web.dto;

import com.blog.web.models.UserEntity;

public class UserPublicDto {
    private String username;

    public UserPublicDto() {
    }

    public UserPublicDto(String username) {
        this.username = username;
    }

    public UserPublicDto(UserEntity user) {
        this.username = user.getUsername();
    }

    public String getUsername() {
        return username;
    }
}
