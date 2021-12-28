package com.browrice.mvcexample.controller;

import com.browrice.mvcexample.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TempController {

    private final TempService tempService;

    @Autowired
    public TempController(TempService tempService) {
        this.tempService = tempService;
    }


    ... // endpoint에 해당하는 함수 작성(GET, POST, PUT, DELETE)

}
