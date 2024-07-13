package com.blog.web.services;

import com.blog.web.dto.RegistrationDto;
import com.blog.web.models.UserEntity;


public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
