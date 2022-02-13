package com.startrip.codebase.controller;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/place")
public class PlaceController {

    private PlaceService placeService;

    @GetMapping("/search")
    public List<Place> finePlace_nameContains(String place_name){
        return this.placeService.findPlace_nameContains(place_name);
    }

    @GetMapping("/search/startwith")
    public List<Place> findItemStartswith(String place_name){
        return this.placeService.findPlace_nameStartwith(place_name);
    }

    @GetMapping("/search/endswith")
    public List<Place> findCompleteItemEndsWith(String place_name){
        return this.placeService.findPlace_nameEndswith(place_name);
    }

    @GetMapping("/gtlt")
    public Map<String, List<Place>> findGTLT(Integer id){
        return this.placeService.findGTLT(id);
    }







}
