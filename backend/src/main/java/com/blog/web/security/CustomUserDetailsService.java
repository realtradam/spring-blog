package com.blog.web.security;

import com.blog.web.models.UserEntity;
import com.blog.web.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findFirstByUsername(username).orElse(new UserEntity());
        if (userEntity.getUsername() != null) {
            return userEntity.toSecurityUser();
        } else {
            throw new UsernameNotFoundException("Invalid username");
        }
    }
}
