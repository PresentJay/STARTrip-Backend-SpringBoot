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

    //sort
    @GetMapping("/sort-repo/viewtime/DESC")
    public List<Place> allSortByRepository() {
        List<Place> placeList = this.placeService.allSortByRepository();
        return placeList;
    }

    @GetMapping("/sort/viewtime/DESC")
    public List<Place> allSortBySort(){
        List<Place> placeList = this.placeService.allSortBySort();
        return placeList;
    }

    @GetMapping("/sort/nameASC-viewtimeDESC")
    public List<Place> allSortListOrder(){
        List<Place> placeList = this.placeService.allSortListOrder();
        return placeList;
    }








}
