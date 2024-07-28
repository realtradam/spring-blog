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

import java.util.*;

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
        final HashSet<Role> roles = (HashSet<Role>) Set.of(roleRepository.findByName("User").orElse(new Role()));
        userRepository.save(new UserEntity(registrationDto.getUsername(), registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()), roles));
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
        Optional<String> optUsername = Optional.ofNullable(SecurityUtil.getSessionUser());
        if(optUsername.isPresent()) {
            user = this.findByUsername(optUsername.get());
        } else {
            user = Optional.empty();
        }
        return user;
    }
}
