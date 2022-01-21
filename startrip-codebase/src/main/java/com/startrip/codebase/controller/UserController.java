package com.startrip.codebase.controller;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "hello docker world";
    }

    @GetMapping("/{userId}")
    public User getRice(@PathVariable("userId") Long id) {
        User user = userService.findOne(id).get();
        return user;
    }

    @GetMapping()
    public List<User> allUser(){
        return userService.findUsers();
    }

    @PostMapping("/new")
    public String create(@RequestParam("name") String name) {
        User user = new User();
        user.setName(name);

        userService.create(user);

        return "저장됨 " + user.getName();
    }
}
