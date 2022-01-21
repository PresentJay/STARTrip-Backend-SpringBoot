package com.startrip.codebase.controller;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User login(@PathVariable("userId") Long id) {
        User user = userService.findOne(id).get();
        return user;
    }

    @GetMapping("/")
    public List<User> allUser(){
        return userService.findUsers();
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("email") String email) {
        User user = new User();
        user.setEmail(email);

        userService.create(user);

        return user.getEmail();
    }
}
