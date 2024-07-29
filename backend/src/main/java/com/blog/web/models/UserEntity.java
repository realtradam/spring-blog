package com.blog.web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "users")
// Named UserEntity to prevent conflicts with Java User object
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id = 0;
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private final Set<Role> roles = new HashSet<>();

    public UserEntity(String username, String email, String password, HashSet<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        setRoles(roles);
    }

    public UserEntity() {

    }

    public boolean equals(UserEntity user) {
        return this.id == user.getId();
    }

    public User toSecurityUser() {
        return new User(this.getEmail(), this.password, this.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }
}
