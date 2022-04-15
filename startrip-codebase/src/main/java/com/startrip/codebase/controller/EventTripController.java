package com.startrip.codebase.controller;

import com.startrip.codebase.domain.event_trip.EventTrip;
import com.startrip.codebase.dto.event_trip.CreateEventTripDto;
import com.startrip.codebase.dto.event_trip.ResponseEventTripDto;
import com.startrip.codebase.dto.event_trip.UpdateEventTripDto;
import com.startrip.codebase.service.trip.EventTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EventTripController {
    private final EventTripService eventTripService;

    @Autowired
    public EventTripController(EventTripService eventTripService) {
        this.eventTripService = eventTripService;
    }

    // Create
    @PostMapping("/eventtrip")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity createEventTrip(@RequestBody CreateEventTripDto dto) {
        UUID id = eventTripService.createEventTrip(dto);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    // All
    @GetMapping("/eventtrip")
    public List<ResponseEventTripDto> getAllEventTrip() {
        List<ResponseEventTripDto> eventTrip = eventTripService.allEventTrip();
        return eventTrip;
    }

    // Get
    @GetMapping("/eventtrip/{id}")
    public ResponseEntity getEventTrip(@PathVariable("id") UUID id) {
        EventTrip eventTrip;
        try {
            eventTrip = eventTripService.getEventTrip(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(eventTrip, HttpStatus.OK);
    }

    // Update
    @PostMapping("/eventtrip/{id}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity updateEventTrip(@PathVariable("id") UUID id, @RequestBody UpdateEventTripDto dto) {
        try{
            eventTripService.updateEventTrip(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/eventtrip/{id}")
    @PreAuthorize("isAuthenticated() and hasAnyRole('USER','ADMIN')")
    public ResponseEntity deleteEventTrip(@PathVariable("id") UUID id){
        try {
            eventTripService.deleteEventTrip(id);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Event Trip 삭제", HttpStatus.OK);
    }
}
