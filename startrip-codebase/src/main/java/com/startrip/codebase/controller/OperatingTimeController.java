package com.startrip.codebase.controller;

import com.startrip.codebase.dto.operatingTime.ResponseOpTimeDto;
import com.startrip.codebase.service.OperatingTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@Slf4j
@RestController
@RequestMapping("/api/place")
public class OperatingTimeController {

     private final OperatingTimeService operatingTimeService;


     public OperatingTimeController(OperatingTimeService operatingTimeService ) {
         this.operatingTimeService = operatingTimeService;
     }

    // CREATE
    @PostMapping("/optime")
    public ResponseEntity createOpTime(@RequestParam Long placeId, ResponseOpTimeDto dto){
         operatingTimeService.createOpTime(placeId, dto);
            // service
            return  new ResponseEntity<>("운영시간 생성",HttpStatus.OK);
    }

    // GET All
    @GetMapping ("/optime/{placeId}")
    public void getOpTimeAll(@PathVariable("placeId") Long placeId){
    }

    // GET All in specific place
    @GetMapping ("/optime/{placeId}")
    public void geOpTimeAll_inSpecificPlace(@PathVariable("placeId") Long placeId){
    }

    // GET All in specific place in specific timestamp
    @GetMapping ("/optime/{placeId}/{timestamp}")
    public void getOpTime_inCurrentTimestamp(@PathVariable("placeId") Long placeId, @PathVariable Timestamp timestamp){
        // TODO: 타임스탬프 형태로 파라미터가 들어올 수 있는 건지, 변혀앻야하는 건지 확인해보자


    }

    // Get specific opTime
    @GetMapping ("/optime/{placeId}/{optimeId}")
    public void getOptime_only(@PathVariable("placeId") Long placeId, @PathVariable("optimeId") Long optimeId){
    }

    // UPDATE opTime
    @PutMapping ("/optime/{placeId}/{optimeId}")
    public void updateOpTime(@PathVariable("placeId") Long placeId, @PathVariable("optimeId") Long optimeId){
    }

    // DELETE opTime
    @DeleteMapping("/optime/{placeId}/{optimeId}")
    public void deleteOpTime(@PathVariable("placeId") Long placeId, @PathVariable("optimeId") Long optimeId){

    }

}
