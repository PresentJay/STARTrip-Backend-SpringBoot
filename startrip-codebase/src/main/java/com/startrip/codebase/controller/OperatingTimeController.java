package com.startrip.codebase.controller;

import com.startrip.codebase.domain.Operating_time.OperatingTime;
import com.startrip.codebase.dto.operatingTime.RequestOptimeDto;
import com.startrip.codebase.dto.operatingTime.UpdateOptimePeriodDto;
import com.startrip.codebase.service.OperatingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OperatingTimeController {

     private final OperatingTimeService operatingTimeService;

     @Autowired
     public OperatingTimeController(OperatingTimeService operatingTimeService ) {
         this.operatingTimeService = operatingTimeService;
     }

    @PostMapping("place/optime")
    public ResponseEntity createOpTime(RequestOptimeDto dto){
         try {
             operatingTimeService.createOpTime(dto);
         } catch(IllegalStateException e){
             return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
         }
         return  new ResponseEntity<>("운영시간 생성",HttpStatus.CREATED);
    }

    // GET : 특정 장소의 모든 op_time
    @GetMapping (path = "place/optime/list", params="placeid")
    public ResponseEntity getOpTimeAll_inSpecificPlace(@RequestParam(value="placeid") UUID placeId){
       List<OperatingTime> operatingTimes;
        try {
            operatingTimes = operatingTimeService.getOptimeAll(placeId);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(operatingTimes, HttpStatus.OK);
    }


    // GET: 특정 시간의 op_time
    @GetMapping (path= "place/optime", params = {"placeid", "requesttime"})
    public ResponseEntity getOpTimeAll_inSpecificTime(@RequestParam(value = "placeid") UUID placeId,
            String requesttime){

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.parse(requesttime, formatterTime);

        Optional<OperatingTime> optime;
        try{
            optime = operatingTimeService.getOpTimeDatetime(placeId, time);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity(optime, HttpStatus.OK);
    }

    // UPDATE opTime
    @PutMapping ("place/optime/{optimeId}")
    public ResponseEntity updateOpTime( @PathVariable("optimeId") UUID optimeId, UpdateOptimePeriodDto dto){
        try {
            operatingTimeService.updateOptime(optimeId, dto);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("운영시간 수정완료", HttpStatus.OK);
    }


    // DELETE opTime
    @DeleteMapping("place/optime/{optimeId}")
    public ResponseEntity deleteOpTime(@PathVariable("optimeId") UUID optimeId){
        try {
            operatingTimeService.deleteOptime(optimeId);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("운영시간 삭제완료", HttpStatus.OK);
    }
}
