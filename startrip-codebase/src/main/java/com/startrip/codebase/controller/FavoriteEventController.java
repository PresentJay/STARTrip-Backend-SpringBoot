package com.startrip.codebase.controller;


import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.favoriteEvent.RequestFavoriteE;
import com.startrip.codebase.service.FavoriteEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FavoriteEventController {

    private final FavoriteEventService favoriteEventService;

    @Autowired
    public FavoriteEventController ( FavoriteEventService favoriteEventService){
        this.favoriteEventService = favoriteEventService;
    }

    @PostMapping("/favoriteevent/{user-id}")
    public ResponseEntity createFavoriteEvent(RequestFavoriteE dto){
        try {
            favoriteEventService.createFavoriteEvent(dto);
        } catch(IllegalStateException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("FavoriteEvent 생성", HttpStatus.CREATED);

    }



}
