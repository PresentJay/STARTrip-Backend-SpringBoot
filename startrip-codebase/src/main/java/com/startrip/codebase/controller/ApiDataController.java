package com.startrip.codebase.controller;

import com.startrip.codebase.util.apidata.PlaceApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/place")
public class ApiDataController {
    private final PlaceApi placeApi;
    public ApiDataController(PlaceApi placeApi) {
        this.placeApi = placeApi;
    }

    @GetMapping("/bigdata")
    public @ResponseBody
    ResponseEntity getPlace() {
        try {
            String date = placeApi.getApiResult("202106");
            return new ResponseEntity(date, HttpStatus.OK);
            //return new ResponseEntity(dataInfoApi.get("202106"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
