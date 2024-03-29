package com.example.demo.controller;

import com.example.demo.datastructures.GenericListTest;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = modelMapper.map(createdUser, UserDto.class);
        return ResponseEntity.ok(createdUserDto);
    }

    @GetMapping("/test")
    @ResponseBody
    public String runGenericListTest() {
        GenericListTest genericListTest = new GenericListTest();
        genericListTest.testGenericList();
        return "GenericListTest executed";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> userList = userService.getAllUsers();
        List<UserDto> userDtoList = userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        model.addAttribute("users", userDtoList);
        return "users";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User updatedUser = userService.updateUser(id, user);
        UserDto updatedUserDto = modelMapper.map(updatedUser, UserDto.class);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
