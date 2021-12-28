package com.browrice.mvcexample.controller;

import com.browrice.mvcexample.domain.user.User;
import com.browrice.mvcexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getRice(@PathVariable("userId") Long id) {
        User user = userService.findOne(id).get();
        return user;
    }

    @PostMapping("/new")
    public String create(@RequestParam("name") String name) {
        User user = new User();
        user.setName(name);

        userService.create(user);

        return "저장됨 " + user.getName();
    }
}
