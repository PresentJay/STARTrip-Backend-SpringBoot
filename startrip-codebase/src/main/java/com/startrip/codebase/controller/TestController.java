package com.startrip.codebase.controller;

import com.startrip.codebase.util.place.PlaceApi;
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

    private final PlaceApi placeApi;
    public TestController(PlaceApi placeApi) {
        this.placeApi = placeApi;
    }
    @GetMapping("/api/place")
    public @ResponseBody ResponseEntity getPlace() {
        try {
            String date = placeApi.getApiResult("202106");
            return new ResponseEntity(date, HttpStatus.OK);
            //return new ResponseEntity(dataInfoApi.get("202106"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
