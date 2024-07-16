package com.blog.web.services.impl;

import com.blog.web.dto.RegistrationDto;
import com.blog.web.models.Role;
import com.blog.web.models.UserEntity;
import com.blog.web.repository.RoleRepository;
import com.blog.web.repository.UserRepository;
import com.blog.web.security.SecurityUtil;
import com.blog.web.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        // this is an unsafe way to store passwords in production
        // it is left this way only because this is a practice project
        //user.setPassword(registrationDto.getPassword());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        final Role role = roleRepository.findByName("User");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity getLoggedInUser() {
        final UserEntity user;
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = this.findByUsername(username);
        }
        else {
            user = new UserEntity();
        }
        return user;
    }
}
