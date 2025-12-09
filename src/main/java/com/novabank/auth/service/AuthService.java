package com.novabank.auth.service;

import com.novabank.auth.api.RegisterRequest;
import com.novabank.auth.user.User;
import com.novabank.auth.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Instant now = Instant.now();

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role("CUSTOMER")
                .status("ACTIVE")
                .createdAt(now)
                .updatedAt(now)
                .build();

        userRepository.save(user);
    }
}
