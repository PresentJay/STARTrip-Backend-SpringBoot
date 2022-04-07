package com.startrip.codebase.controller;

import com.startrip.codebase.domain.Operating_time.OperatingTime;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.dto.operatingTime.ResponseOpTimeDto;
import com.startrip.codebase.service.OperatingTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


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
    public ResponseEntity createOpTime(ResponseOpTimeDto dto){
        operatingTimeService.createOpTime(dto);
         return  new ResponseEntity<>("운영시간 생성",HttpStatus.OK);
    }

    // GET : api/place/optime?palceId={placeId}  // 특정 장소의 모든 op_time 보기
    @GetMapping ("/optime")
    public ResponseEntity geOpTimeAll_inSpecificPlace(@RequestParam Long placeId ){
       List<OperatingTime> operatingTimes;
        try {
            operatingTimes = operatingTimeService.getOptimeAll(placeId);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(operatingTimes, HttpStatus.OK);
    }



    // GET api/place/optime?placeId={placeId}&datetime={datetime} // 특정 시간의 특정장소 op_time 보기
    @GetMapping ("/optime")
    public void getOpTime_inCurrentTimestamp(@RequestParam UUID placeId,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        log.info(String.valueOf(date));
    }


    // UPDATE opTime
    @PutMapping ("/optime/{optimeId}")
    public void updateOpTime(@PathVariable("placeId") Long placeId, @PathVariable("optimeId") Long optimeId){
    }

    // DELETE opTime
    @DeleteMapping("/optime/{placeId}/{optimeId}")
    public void deleteOpTime(@PathVariable("placeId") Long placeId, @PathVariable("optimeId") Long optimeId){

    }

}
