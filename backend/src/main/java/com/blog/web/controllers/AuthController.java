package com.blog.web.controllers;

import com.blog.web.dto.RegistrationDto;
import com.blog.web.models.UserEntity;
import com.blog.web.services.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userlogin")
    public String login(Model model) {
        final UserEntity user = userService.getLoggedInUser().orElse(new UserEntity());
        model.addAttribute("user", user);
        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        final RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult result, Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail()).orElse(null);
        if (existingUserEmail != null && StringUtils.isBlank(existingUserEmail.getEmail())) {
            result.rejectValue("email", "There is already a user with this email");
        }

        UserEntity existingUsername = userService.findByUsername(user.getUsername()).orElse(null);
        if (existingUsername != null && StringUtils.isBlank(existingUsername.getUsername())) {
            result.rejectValue("username", "There is already a user with this username");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/articles?success";
    }
}
