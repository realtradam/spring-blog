package com.blog.web.services;

import com.blog.web.dto.RegistrationDto;
import com.blog.web.models.UserEntity;

import java.util.Optional;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    public Optional<UserEntity> getLoggedInUser();
}
