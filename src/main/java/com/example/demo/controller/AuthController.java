package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        String token = authService.registerUser(userDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/super/register")
    public ResponseEntity<String> registerAdmin(@RequestBody UserDto userDto) {
        String token = authService.registerAdmin(userDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto, HttpServletResponse response) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        UserDto loggedInUserDto = authService.loginUser(username, password);
        String token = loggedInUserDto.getToken();
        String cookieValue = "Bearer " + token;
        cookieValue = cookieValue.replaceAll(" ", "");
        Cookie cookie = new Cookie("Auth", cookieValue);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(loggedInUserDto);
    }
}
