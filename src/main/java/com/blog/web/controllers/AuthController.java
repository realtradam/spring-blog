package com.blog.web.controllers;

import com.blog.web.dto.RegistrationDto;
import com.blog.web.models.UserEntity;
import com.blog.web.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult result,
                           Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if(
                existingUserEmail != null &&
                existingUserEmail.getEmail() != null &&
                !existingUserEmail.getEmail().isEmpty()
        ) {
            result.rejectValue("email", "There is already a user with this email");
        }

        UserEntity existingUsername = userService.findByUsername(user.getUsername());
        if(
                existingUsername != null &&
                existingUsername.getUsername() != null &&
                !existingUsername.getUsername().isEmpty()
        )
        {
            result.rejectValue("username", "There is already a user with this username");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/articles?success";
    }
}
