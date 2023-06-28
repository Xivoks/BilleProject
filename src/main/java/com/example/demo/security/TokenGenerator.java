package com.example.demo.security;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenGenerator {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static boolean verifyToken(String token) {
        return token != null && !token.isEmpty();
    }
}
