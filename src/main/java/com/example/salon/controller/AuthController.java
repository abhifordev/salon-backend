package com.example.salon.controller;

import com.example.salon.dto.JwtResponse;
import com.example.salon.dto.LoginRequest;
import com.example.salon.dto.RegisterRequest;
import com.example.salon.entity.Role;
import com.example.salon.entity.User;
import com.example.salon.repository.RoleRepository;
import com.example.salon.service.AuthService;
import com.example.salon.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // PUBLIC → CUSTOMER
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // ADMIN → EMPLOYEE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/register-employee")
    public ResponseEntity<Void> registerEmployee(@RequestBody RegisterRequest request) {
        authService.registerEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public JwtResponse refresh(@RequestParam String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
