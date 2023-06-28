package com.example.demo.datastructures;

import com.example.demo.model.User;
import com.example.demo.role.Role;

import java.util.List;

public class GenericListTest {
    public void testGenericList() {
        GenericList<User> userList = new GenericList<>();

        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setRole(Role.USER);
        user1.setPassword("password1");
        user1.setToken("token1");
        userList.add(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setRole(Role.USER);
        user2.setPassword("password2");
        user2.setToken("token2");
        userList.add(user2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setEmail("user3@example.com");
        user3.setRole(Role.ADMIN);
        user3.setPassword("password3");
        user3.setToken("token3");
        userList.add(user3);

        List<User> users = userList.getList();
        for (User user : users) {
            System.out.println(user.getUsername());
        }
    }
}
