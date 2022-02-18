package com.startrip.codebase.controller;

import com.nimbusds.oauth2.sdk.TokenResponse;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/user")
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @PostMapping("/login")
    public String login(){
        return "로그인";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("email") String email) {
        User user = new User();
        user.setEmail(email);

        userService.create(user);

        return user.getEmail();
    }
}