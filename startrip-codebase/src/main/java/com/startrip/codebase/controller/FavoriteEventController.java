package com.startrip.codebase.controller;

import com.startrip.codebase.domain.favorite_event.FavoriteEvent;
import com.startrip.codebase.domain.operating_time.OperatingTime;
import com.startrip.codebase.dto.favoriteEvent.RequestFavoriteE;
import com.startrip.codebase.dto.favoriteEvent.UpdateFavoriteE;
import com.startrip.codebase.service.FavoriteEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FavoriteEventController {

    private final FavoriteEventService favoriteEventService;

    @Autowired
    public FavoriteEventController ( FavoriteEventService favoriteEventService){
        this.favoriteEventService = favoriteEventService;
    }

    @PostMapping("{userId}/favoriteevent") //TODO: {userId}/favoriteevent
    public ResponseEntity createFavoriteEvent(@PathVariable("userId") Long userId, RequestFavoriteE dto){ // TODO: (@PathVariable("userId") Long userId)

        try {
            favoriteEventService.createFavoriteEvent(userId, dto);
        } catch(IllegalStateException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("FavoriteEvent 생성", HttpStatus.CREATED);

    }

    @GetMapping("{userId}/favoriteevent/")
    public ResponseEntity getFavoriteEvent(@PathVariable("userId") Long userId){

        List<FavoriteEvent> favoriteEvents;
        try{
            favoriteEvents = favoriteEventService.getFavoriteEvent(userId);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(favoriteEvents, HttpStatus.OK);
    }

    @PutMapping("{userid}/favoriteevent/{fEventId}")
    public ResponseEntity updateFavoriteEvent(UpdateFavoriteE dto){
        try{
            favoriteEventService.updateFavoriteEvent(dto);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("이벤트좋아요 수행여부 수정완료", HttpStatus.OK);
    }

    @DeleteMapping ("{userid}/favoriteevent/{fEventId}")
    public ResponseEntity deleteFavoriteEvent(@PathVariable("fEventId") UUID fEventId){
        try{
            favoriteEventService.deleteFavoriteEvent(fEventId);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("이벤트좋아요 삭제 요청 완료(10분 후 삭제)", HttpStatus.OK);
    }
}

