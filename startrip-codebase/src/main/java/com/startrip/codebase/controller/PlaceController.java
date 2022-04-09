package com.startrip.codebase.controller;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place_info.PlaceInfo;
import com.startrip.codebase.dto.PlaceDto;
import com.startrip.codebase.dto.PlaceInfoDto;
import com.startrip.codebase.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @Secured("ROLE_ADMIN")
    @PostMapping("/place")
    public @ResponseBody
    ResponseEntity addPlace(PlaceDto dto) {
        UUID id;
        try{
            id = placeService.createPlace(dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id.toString(), HttpStatus.OK);
    }

    @GetMapping("/place/list")
    public @ResponseBody
    ResponseEntity showPlace() {
        return new ResponseEntity(placeService.allPlace(), HttpStatus.OK);
    }

    @GetMapping("/place/list/{categoryId}")
    public @ResponseBody
    ResponseEntity showPlaceByCategory(@PathVariable("categoryId") String category_id) {
        List<Place> placeList;
        try{
            placeList = placeService.categoryPlace(UUID.fromString(category_id));
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(placeList, HttpStatus.OK);
    }

    @GetMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity getPlace(@PathVariable("id") String id) {
        Place place;
        try{
            place = placeService.getPlace(UUID.fromString(id));
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(place, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity updatePlace(@PathVariable("id") String id, PlaceDto dto) {
        try{
            placeService.updatePlace(UUID.fromString(id), dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/place/{id}")
    public @ResponseBody
    ResponseEntity deletePlace(@PathVariable("id") String id) {
        try{
            placeService.deletePlace(UUID.fromString(id));
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }

}
