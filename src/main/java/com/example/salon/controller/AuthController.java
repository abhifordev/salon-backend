package com.example.salon.controller;

import com.example.salon.dto.JwtResponse;
import com.example.salon.dto.LoginRequest;
import com.example.salon.dto.RegisterRequest;
import com.example.salon.entity.User;
import com.example.salon.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public JwtResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public JwtResponse refresh(
            @RequestParam String refreshToken) {

        return authService.refresh(refreshToken);
    }

    @GetMapping("/me")
    public User me() {
        return authService.getCurrentUser();
    }
}
