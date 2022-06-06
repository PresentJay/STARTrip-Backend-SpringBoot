package com.startrip.codebase.curation.filterController;

import com.startrip.codebase.curation.curationDto.RequestLocationDto;
import com.startrip.codebase.curation.curationDto.ResponseLocationDto;
import com.startrip.codebase.curation.filterService.LocationFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationFilterController {

    private LocationFilterService locationFilterService;

    @Autowired
    public LocationFilterController(LocationFilterService locationFilterService){
        this.locationFilterService = locationFilterService;
    }

    @PostMapping("/locationFilter")
    public ResponseEntity getLocationFilterService(@RequestBody RequestLocationDto dto) {

        Double[] userInputLocate = new Double[2];
        userInputLocate[0] = dto.getLatitude();
        userInputLocate[1] = dto.getLongitude();

        List<ResponseLocationDto> locationFilterPlace;
        try {
            locationFilterPlace = locationFilterService.getPlaceList(userInputLocate);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(locationFilterPlace, HttpStatus.OK);
    }
}
