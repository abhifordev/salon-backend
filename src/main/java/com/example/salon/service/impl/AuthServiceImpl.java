package com.example.salon.service.impl;

import com.example.salon.dto.JwtResponse;
import com.example.salon.dto.LoginRequest;
import com.example.salon.dto.RegisterRequest;
import com.example.salon.entity.AuthProvider;
import com.example.salon.entity.Role;
import com.example.salon.entity.User;
import com.example.salon.exception.BadRequestException;
import com.example.salon.exception.UnauthorizedException;
import com.example.salon.repository.RoleRepository;
import com.example.salon.repository.UserRepository;
import com.example.salon.security.JwtTokenProvider;
import com.example.salon.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // PUBLIC → CUSTOMER ONLY
    @Override
    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("ROLE_CUSTOMER missing"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        user.setEnabled(true);
        user.getRoles().add(customerRole);

        userRepository.save(user);
    }

    // ADMIN → EMPLOYEE ONLY
    @Override
    public void registerEmployee(RegisterRequest request) {

        Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("ROLE_EMPLOYEE missing"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        user.setEnabled(true);
        user.getRoles().add(employeeRole);

        userRepository.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new JwtResponse(
                jwtTokenProvider.generateToken(user.getEmail(), roles),
                jwtTokenProvider.generateRefreshToken(user.getEmail())
        );
    }

    @Override
    public JwtResponse refresh(String refreshToken) {

        String email = jwtTokenProvider.getUsername(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new JwtResponse(
                jwtTokenProvider.generateToken(email, roles),
                jwtTokenProvider.generateRefreshToken(email)
        );
    }

    @Override
    public User getCurrentUser() {

        Object principal =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UnauthorizedException("User not found"));
        }

        throw new UnauthorizedException("Unauthorized");
    }

}
