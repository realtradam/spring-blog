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
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        final Role role = roleRepository.findByName("User").orElse(new Role());
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> getLoggedInUser() {
        final Optional<UserEntity> user;
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            user = this.findByUsername(username);
        } else {
            user = Optional.of(new UserEntity());
        }
        return user;
    }
}
