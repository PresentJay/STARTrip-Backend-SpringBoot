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

    @GetMapping("/favoriteevent/{user-id}")
    public ResponseEntity getFavoriteEvent(RequestFavoriteE dto){

        List<FavoriteEvent> favoriteEvents;
        try{
            favoriteEvents = favoriteEventService.getFavoriteEvent(dto);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(favoriteEvents, HttpStatus.OK);
    }

    @PostMapping("/favoriteevent/{fEvent_id}")
    public ResponseEntity updateFavoriteEvent(UpdateFavoriteE dto){



        return new ResponseEntity("수정되었습니다", HttpStatus.OK); // TODO: check, 이것은 사실 "삭제되었습니다"가 맞지 않을까?
    }

}
