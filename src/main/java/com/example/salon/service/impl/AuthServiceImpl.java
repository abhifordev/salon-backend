package com.example.salon.service.impl;

import com.example.salon.dto.JwtResponse;
import com.example.salon.dto.LoginRequest;
import com.example.salon.dto.RegisterRequest;
import com.example.salon.entity.Role;
import com.example.salon.entity.User;
import com.example.salon.exception.BadRequestException;
import com.example.salon.exception.UnauthorizedException;
import com.example.salon.repository.RoleRepository;
import com.example.salon.repository.UserRepository;
import com.example.salon.security.JwtTokenProvider;
import com.example.salon.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("ROLE_CUSTOMER not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider("LOCAL");
        user.getRoles().add(customerRole);

        userRepository.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String accessToken = jwtTokenProvider.generateToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(String refreshToken) {

        String email = jwtTokenProvider.getUsername(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        return new JwtResponse(
                jwtTokenProvider.generateToken(user.getEmail()),
                jwtTokenProvider.generateRefreshToken(user.getEmail())
        );
    }

    @Override
    public User getCurrentUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
