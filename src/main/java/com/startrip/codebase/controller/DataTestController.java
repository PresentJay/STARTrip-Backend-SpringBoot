package com.startrip.codebase.controller;

import com.startrip.codebase.util.datatest.AreaBased;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class DataTestController {
    @Autowired
    private final AreaBased areaBased;

    @Autowired
    public DataTestController(AreaBased areaBased) {
        this.areaBased = areaBased;
    }

    @GetMapping("/datatest")
    public @ResponseBody
    ResponseEntity getPlace() {
        try {
            //String date = dataTest.getApiResult();
            return new ResponseEntity(areaBased.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
