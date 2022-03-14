package com.startrip.codebase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/ping")
    public String test() {
        return "pong";
    }

    @GetMapping("/api/test")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public @ResponseBody
    ResponseEntity jwtTest() {
        return new ResponseEntity("정상 요청.", HttpStatus.OK);
    }
}
