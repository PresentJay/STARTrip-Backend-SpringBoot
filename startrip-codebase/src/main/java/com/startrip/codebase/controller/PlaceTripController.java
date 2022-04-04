package com.startrip.codebase.controller;

import com.startrip.codebase.domain.place_trip.PlaceTrip;
import com.startrip.codebase.dto.place_trip.CreatePlaceTripDto;
import com.startrip.codebase.dto.place_trip.ResponsePlaceTripDto;
import com.startrip.codebase.dto.place_trip.UpdatePlaceTripDto;
import com.startrip.codebase.service.trip.PlaceTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PlaceTripController {
    private final PlaceTripService placeTripService;

    @Autowired
    public PlaceTripController(PlaceTripService placeTripService) {
        this.placeTripService = placeTripService;
    }

    // Create
    @PostMapping("/placetrip")
    public ResponseEntity createPlaceTrip(@RequestBody CreatePlaceTripDto dto) {
        placeTripService.createPlaceTrip(dto);
        return new ResponseEntity("Place Trip 생성", HttpStatus.OK);
    }

    // All
    @GetMapping("/placetrip")
    public List<ResponsePlaceTripDto> getAllPlaceTrip() {
        List<ResponsePlaceTripDto> placeTrip = placeTripService.allPlaceTrip();
        return placeTrip;
    }

    // Get
    @GetMapping("/placetrip/{id}")
    public ResponseEntity getPlaceTrip(@PathVariable("id") UUID id) {
        PlaceTrip placeTrip;
        try {
            placeTrip = placeTripService.getPlaceTrip(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(placeTrip, HttpStatus.OK);
    }

    // Update
    @PostMapping("/placetrip/{id}")
    public ResponseEntity updatePlaceTrip(@PathVariable("id") UUID id, @RequestBody UpdatePlaceTripDto dto) {
        try{
            placeTripService.updatePlaceTrip(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Place Trip 업데이트", HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/placetrip/{id}")
    public ResponseEntity deletePlaceTrip(@PathVariable("id") UUID id){
        try {
            placeTripService.deletePlaceTrip(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Place Trip 삭제", HttpStatus.OK);
    }
}
