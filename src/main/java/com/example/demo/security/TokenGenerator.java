package com.example.demo.security;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenGenerator {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}