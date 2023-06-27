package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.role.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public String registerUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        user.setRole(Role.USER);
        User createdUser = userService.createUser(user);

        return createdUser.getToken();
    }

    public String registerAdmin(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        user.setRole(Role.ADMIN);
        User createdUser = userService.createUser(user);

        return createdUser.getToken();
    }

    public UserDto loginUser(String username, String password) {
        User user = userService.loginUser(username, password);
        return modelMapper.map(user, UserDto.class);
    }

}
