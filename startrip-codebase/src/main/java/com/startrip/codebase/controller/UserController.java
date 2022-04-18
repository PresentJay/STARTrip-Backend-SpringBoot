package com.startrip.codebase.controller;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.LoginDto;
import com.startrip.codebase.dto.SignUpDto;
import com.startrip.codebase.jwt.JwtFilter;
import com.startrip.codebase.jwt.TokenProvider;
import com.startrip.codebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public UserController(UserService userService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    public @ResponseBody
    ResponseEntity login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody
    ResponseEntity logout() {
        SecurityContextHolder.clearContext();
        // TODO : 토큰 해제 로직 추가
        return new ResponseEntity<>("로그아웃", HttpStatus.OK);
    }

    // Auth End-point
    @GetMapping("/auth/success")
    public @ResponseBody
    ResponseEntity login(@RequestParam("token") String token) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }


    @PostMapping("/signup")
    public @ResponseBody
    ResponseEntity signup(@RequestBody SignUpDto signUpDto) {

        try {
            User user = User.createUser(signUpDto);
            userService.create(user);
            return new ResponseEntity(signUpDto.getEmail() + " 가입완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity getTest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity(authentication, HttpStatus.OK);
    }
}