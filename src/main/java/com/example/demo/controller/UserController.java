package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        logger.info("Creating a new user");
        User user = modelMapper.map(userDto, User.class);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = modelMapper.map(createdUser, UserDto.class);
        logger.info("User created with ID: {}", createdUserDto.getId());
        return ResponseEntity.ok(createdUserDto);
    }

    @GetMapping
    public String getAllUsers(Model model) {
        logger.info("Getting all users");
        List<User> userList = userService.getAllUsers();
        List<UserDto> userDtoList = userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        model.addAttribute("users", userDtoList);
        logger.info("Returned {} users", userDtoList.size());
        return "users";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        logger.info("Getting user with ID: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            logger.info("User found with ID: {}", id);
            return ResponseEntity.ok(userDto);
        }
        logger.info("User not found with ID: {}", id);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto updatedUserDto) {
        logger.info("Updating user with ID: {}", id);
        User updatedUser = modelMapper.map(updatedUserDto, User.class);
        User user = userService.updateUser(id, updatedUser);
        if (user != null) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            logger.info("User updated with ID: {}", id);
            return ResponseEntity.ok(userDto);
        }
        logger.info("User not found with ID: {}", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            logger.info("User deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        logger.info("User not found with ID: {}", id);
        return ResponseEntity.notFound().build();
    }
}
