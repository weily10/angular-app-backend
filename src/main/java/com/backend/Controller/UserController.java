package com.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.backend.Model.User;
import com.backend.Repository.UserRepository;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginData) {
        User user = userRepo.findByEmail(loginData.getEmail());

        if (user == null || !user.getPassword().equals(loginData.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
        }

        String secretString = "your_super_secret_key_at_least_32_characters_long";
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

// 2. Build the token using the new 'claims()' method instead of 'setClaims()'
        String token = Jwts.builder()
                .subject(user.getEmail())
                .claim("role", "USER") // Use .claim() for individual items
                // OR use .claims(myMap) if you have a full map
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(key) // No longer needs SignatureAlgorithm.HS256 (it infers it from key)
                .compact();


        user.setPassword(null);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

}
