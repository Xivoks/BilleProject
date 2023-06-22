package com.example.demo.dto;

import com.example.demo.role.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private String token;
}