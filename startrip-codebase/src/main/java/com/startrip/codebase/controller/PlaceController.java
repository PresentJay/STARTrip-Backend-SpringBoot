package com.startrip.codebase.controller;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place_info.PlaceInfo;
import com.startrip.codebase.dto.PlaceDto;
import com.startrip.codebase.dto.PlaceInfoDto;
import com.startrip.codebase.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


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
        try{
            placeService.createPlace(dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("생성되었습니다", HttpStatus.OK);
    }

    @GetMapping("/place/list")
    public @ResponseBody
    ResponseEntity showPlace() {
        return new ResponseEntity(placeService.allPlace(), HttpStatus.OK);
    }

    @GetMapping("/place/list/{categoryId}")
    public @ResponseBody
    ResponseEntity showPlaceByCategory(@PathVariable("categoryId") Long category_id) {
        List<Place> placeList;
        try{
            placeList = placeService.categoryPlace(category_id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(placeList, HttpStatus.OK);
    }

    @GetMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity getPlace(@PathVariable("id") Long id) {
        Place place;
        try{
            place = placeService.getPlace(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(place, HttpStatus.OK);
    }

    @PostMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity updatePlace(@PathVariable("id") Long id, PlaceDto dto) {
        try{
            placeService.updatePlace(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @DeleteMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity deletePlace(@PathVariable("id") Long id) {
        try{
            placeService.deletePlace(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }

}
