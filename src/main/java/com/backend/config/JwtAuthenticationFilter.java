package com.backend.config;

import com.backend.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
     protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String path = request.getServletPath();

        System.out.println(">>> FILTER START: Checking path: " + path);

        // 1. THE BYPASS: If it's an auth path, just let it go to the Controller!
        if (path.contains("/auth")) {
            System.out.println(">>> BYPASS: Path is /auth, skipping JWT check.");
            filterChain.doFilter(request, response);
            return;
        }

        // 2. THE NULL CHECK: If no header, don't try to substring it!
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(">>> EXIT: No Bearer token found. Blocking protected path.");
            filterChain.doFilter(request, response); // Let Spring's SecurityConfig handle the 403
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String userEmail = jwtService.extractUsername(jwt);
            System.out.println(">>> EXTRACTED EMAIL: " + userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtService.isTokenValid(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userEmail, null, new ArrayList<>()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println(">>> SUCCESS: User authenticated.");
                } else {
                    System.out.println(">>> FAILURE: Token invalid.");
                }
            }
        } catch (Exception e) {
            System.out.println(">>> ERROR: Filter Exception: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}