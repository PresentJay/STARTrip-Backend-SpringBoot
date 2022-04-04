package com.startrip.codebase.controller;

import com.startrip.codebase.domain.state.State;
import com.startrip.codebase.dto.state.CreateStateDto;
import com.startrip.codebase.dto.state.UpdateStateDto;
import com.startrip.codebase.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class StateController {
    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    // Create
    @PostMapping("/state")
    public ResponseEntity createState(@RequestBody CreateStateDto dto) {
        stateService.createState(dto);
        return new ResponseEntity("State 생성", HttpStatus.OK);
    }

    // Get
    @GetMapping("/state/{id}")
    public ResponseEntity getPlaceTrip(@PathVariable("id") UUID id) {
        State state;
        try {
            state = stateService.getState(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(state, HttpStatus.OK);
    }

    // Update
    @PostMapping("/state/{id}")
    public ResponseEntity updateState(@PathVariable("id") UUID id, @RequestBody UpdateStateDto dto) {
        try{
            stateService.updateState(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("State 업데이트", HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/state/{id}")
    public ResponseEntity deleteState(@PathVariable("id") UUID id){
        try {
            stateService.deleteState(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("State 삭제", HttpStatus.OK);
    }
}
