package com.startrip.codebase.controller;

import com.startrip.codebase.domain.place_info.PlaceInfo;
import com.startrip.codebase.dto.PlaceInfoDto;
import com.startrip.codebase.service.PlaceInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlaceInfoController {

    private PlaceInfoService placeInfoService;

    public PlaceInfoController(PlaceInfoService placeInfoService) {
        this.placeInfoService = placeInfoService;
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PostMapping("/place/info")
    public @ResponseBody
    ResponseEntity addPlaceInfo(@RequestBody PlaceInfoDto dto) {
        Long id;
        try{
            id = placeInfoService.createPlaceInfo(dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @GetMapping("/place/info/{id}")
    public @ResponseBody
    ResponseEntity getPlaceInfo(@PathVariable("id") Long id) {
        PlaceInfo placeInfo;
        try{
            placeInfo = placeInfoService.getPlaceInfo(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(placeInfo, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @PostMapping("/place/info/{id}")
    public @ResponseBody
    ResponseEntity updatePlaceInfo(@PathVariable("id") Long id, PlaceInfoDto dto) {
        try{
            placeInfoService.updatePlaceInfo(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @DeleteMapping("/place/info/{id}")
    public @ResponseBody
    ResponseEntity deletePlaceInfo(@PathVariable("id") Long id) {
        try{
            placeInfoService.deletePlaceInfo(id);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }
}
