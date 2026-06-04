package com.example.AuthApp.controller;

import com.example.AuthApp.dto.LoginRequest;
import com.example.AuthApp.dto.RegisterRequest;
import com.example.AuthApp.model.User;
import com.example.AuthApp.security.JwtFilter;
import com.example.AuthApp.security.Jwtutil;
import com.example.AuthApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {
    private UserService userService;
    private final Jwtutil jwtutil;

    public UserController(UserService userService, Jwtutil jwtutil) {
        this.userService = userService;
        this.jwtutil = jwtutil;
    }

    // register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // save to db
        userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok("User Registered Successfully.");
    }

    // login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> user = userService.findByEmail(request.getEmail());

        if(user.isPresent() && userService.passwordMatch(request.getPassword(), user.get().getPassword())) {
            String token = jwtutil.generateToken(request.getEmail());

            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
    }
}
