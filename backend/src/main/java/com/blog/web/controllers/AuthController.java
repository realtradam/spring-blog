package com.blog.web.controllers;

import com.blog.web.dto.RegistrationDto;
import com.blog.web.models.UserEntity;
import com.blog.web.services.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //@PostMapping("/register/save")
    @PostMapping("/register")
    public RegistrationDto register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail()).orElse(null);
        if (existingUserEmail != null && StringUtils.isBlank(existingUserEmail.getEmail())) {
            result.rejectValue("email", "There is already a user with this email");
        }

        UserEntity existingUsername = userService.findByUsername(user.getUsername()).orElse(null);
        if (existingUsername != null && StringUtils.isBlank(existingUsername.getUsername())) {
            result.rejectValue("username", "There is already a user with this username");
        }

        if (!result.hasErrors()) {
            userService.saveUser(user);
        }
        return user;
    }
}
