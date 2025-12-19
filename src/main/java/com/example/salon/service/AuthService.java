package com.example.salon.service;

import com.example.salon.dto.JwtResponse;
import com.example.salon.dto.LoginRequest;
import com.example.salon.dto.RegisterRequest;
import com.example.salon.entity.User;

public interface AuthService {

    void register(RegisterRequest request);

    JwtResponse login(LoginRequest request);

    JwtResponse refresh(String refreshToken);

    User getCurrentUser();
}
