package com.startrip.codebase.controller;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.dto.NewEventDto;
import com.startrip.codebase.domain.event.dto.ResponseEventDto;
import com.startrip.codebase.domain.event.dto.UpdateEventDto;
import com.startrip.codebase.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public List<ResponseEventDto> getEvents() {
        List<ResponseEventDto> events = eventService.allEvent();
        return events;
    }

    @PostMapping("/event")
    public ResponseEntity
    createEvent(@RequestBody NewEventDto dto) {
        eventService.createEvent(dto);
        return new ResponseEntity("이벤트 생성", HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public Event getEvent(@PathVariable("id") Long id) {
        Event event = eventService.getEvent(id);
        return event;
    }

    @PostMapping("/event/{id}")
    public ResponseEntity updateEvent(@PathVariable("id") Long id, @RequestBody UpdateEventDto dto) {
        try{
            eventService.updateEvent(id, dto);

        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }


    @DeleteMapping("/event/{id}")
    public String deleteEvent(@PathVariable("id") Long id){
        eventService.deleteEvent(id);
        return "삭제";
    }


}
