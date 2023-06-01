package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static List<User> userList = new ArrayList<>();

    public User createUser(User user) {
        userList.add(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public User getUserById(Long id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User updateUser(Long id, User updatedUser) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());
                return user;
            }
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                userList.remove(user);
                return true;
            }
        }
        return false;
    }
}
