package dev.accessaid.AccessAid.User.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> seeAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("")
    public User addUser(@RequestBody User user) {

        return userService.createUser(user);
    }

}
