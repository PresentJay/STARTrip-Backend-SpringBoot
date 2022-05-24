package com.startrip.codebase.controller;

import com.startrip.codebase.domain.favorite_event.FavoriteEvent;
import com.startrip.codebase.dto.favoriteEvent.RequestFavoriteEventDto;
import com.startrip.codebase.dto.favoriteEvent.ResponseFavoriteEventDto;
import com.startrip.codebase.dto.favoriteEvent.UpdateFavoriteEventDto;
import com.startrip.codebase.service.FavoriteEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FavoriteEventController {

    private final FavoriteEventService favoriteEventService;

    @Autowired
    public FavoriteEventController ( FavoriteEventService favoriteEventService){
        this.favoriteEventService = favoriteEventService;
    }

    @PostMapping("user/{userId}/event/favoriteevent")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity createFavoriteEvent(@PathVariable("userId") Long userId, RequestFavoriteEventDto dto){
        try {
            favoriteEventService.createFavoriteEvent(userId, dto);
        } catch(IllegalStateException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("FavoriteEvent 생성", HttpStatus.CREATED);
    }

    @GetMapping("user/{userId}/event/favoriteevent/")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity getFavoriteEvent(@PathVariable("userId") Long userId){
        List<ResponseFavoriteEventDto> responseFavoriteEventDtos;
        try{
            responseFavoriteEventDtos = favoriteEventService.getFavoriteEvent(userId);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(responseFavoriteEventDtos, HttpStatus.OK);
    }

    @PutMapping("user/{userid}/event/favoriteevent/{favoriteeventId}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity updateFavoriteEvent(@PathVariable("favoriteeventId") UUID favoriteeventId, UpdateFavoriteEventDto dto){
        try{
            favoriteEventService.updateFavoriteEvent(favoriteeventId, dto);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("이벤트좋아요 수행여부 수정완료", HttpStatus.OK);
    }

    @DeleteMapping ("user/{userid}/event/favoriteevent/{favoriteeventId}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity deleteFavoriteEvent(@PathVariable("favoriteeventId") UUID favoriteeventId){
        try{
            favoriteEventService.deleteFavoriteEvent(favoriteeventId);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("이벤트좋아요 삭제 요청 완료(30분 제한, 1시간마다 확인 후 삭제처리)", HttpStatus.OK);
    }
}

