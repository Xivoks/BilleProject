package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static List<User> userList = new ArrayList<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        userList.add(user);
        return user;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userList);
        return "users";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                userList.remove(user);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
