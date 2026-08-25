package com.backend.security;

import com.backend.Model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class CustomUserDetail implements UserDetails {

    // Custom Getters to access your extra fields later
    @Getter
    private final String id;
    private final String email;
    @Getter
    private final String username; // usually email or username
    private final String password;

    // Constructor: Map your MongoDB User entity to this Security object
    public CustomUserDetail(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    // Required overrides by Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Add roles here if you use them (e.g., ROLE_USER)
    }

    @Override
    public String getPassword() { return password; }


    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}