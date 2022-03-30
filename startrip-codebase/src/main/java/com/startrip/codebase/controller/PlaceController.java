package com.startrip.codebase.controller;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.dto.PlaceDto;
import com.startrip.codebase.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @PostMapping("/place")
    public @ResponseBody
    ResponseEntity addPlace(PlaceDto dto) {
        placeService.createPlace(dto);
        return new ResponseEntity("생성되었습니다", HttpStatus.OK);
    }

    @GetMapping("/place/list")
    public @ResponseBody
    ResponseEntity showPlace() {
        return new ResponseEntity(placeService.allPlace(), HttpStatus.OK);
    }

    @GetMapping("/place/list/{categoryId}")
    public @ResponseBody
    ResponseEntity showPlaceByCategory(@PathVariable("categoryId") Integer category_id) {
        return new ResponseEntity(placeService.categoryPlace(category_id), HttpStatus.OK);
    }

    @GetMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity getPlace(@PathVariable("id") UUID id) {
        return new ResponseEntity(placeService.getPlace(id), HttpStatus.OK);
    }

    @PostMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity updatePlace(@PathVariable("id") UUID id, PlaceDto dto) {
        placeService.updatePlace(id, dto);
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @DeleteMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity deleteNotice(@PathVariable("id") UUID id) {
        placeService.deletePlace(id);
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }
}
