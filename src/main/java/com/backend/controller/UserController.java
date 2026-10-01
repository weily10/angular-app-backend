package com.backend.controller;

import com.backend.dto.RegisterRequest;
import com.backend.Model.User;
import com.backend.repository.UserRepository;
import com.backend.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest regData) {
        if(userRepo.existsByEmail(regData.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: email already in use");
        }

        if(!regData.getPassword().equals(regData.getConfirmPassword())){
            return ResponseEntity.badRequest().body("passwords dont match");
        }

        User user = new User();
        user.setEmail(regData.getEmail());
        // FIXED: Encode the primary password field
        user.setPassword(passwordEncoder.encode(regData.getPassword()));

        User savedUser = userRepo.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginData) {

        User user = userRepo.findByEmail(loginData.getEmail())
                .orElse(null);

        if (user == null) {
            System.out.println("Login Failed: User not found with email: " + loginData.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
        }

        String testHash = passwordEncoder.encode(loginData.getPassword());


        boolean isPasswordMatch = passwordEncoder.matches(loginData.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            System.out.println("Login Failed: Password mismatch for user: " + loginData.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        user.setPassword(null);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> user(){

        return null;
    }

}
