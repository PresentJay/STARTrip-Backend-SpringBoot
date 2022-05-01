package com.startrip.codebase.controller;

import com.startrip.codebase.domain.event_pricing.EventPricingService;
import com.startrip.codebase.domain.event_pricing.dto.EventPricingDto;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event/pricing")
public class EventPricingController {

    private EventPricingService eventPricingService;

    @Autowired
    public EventPricingController(EventPricingService eventPricingService) {
        this.eventPricingService = eventPricingService;
    }

    @PreAuthorize("isAuthenticated() and hasAnyRole('EVENT_CREATOR', 'ADMIN')")
    @PostMapping("/new")
    public ResponseEntity newPricing(@RequestBody EventPricingDto dto) {
        try {

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }
}
