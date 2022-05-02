package com.startrip.codebase.domain.event_pricing;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event_pricing.dto.EventPricingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPricingService {

    private EventPricingRepository eventPricingRepository;

    private EventRepository eventRepository;

    @Autowired
    public EventPricingService(EventPricingRepository eventPricingRepository, EventRepository eventRepository) {
        this.eventPricingRepository = eventPricingRepository;
        this.eventRepository = eventRepository;
    }

    public void newPricing(Long eventId, EventPricingDto dto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("해당 이벤트가 없습니다"));
        EventPricing eventPricing = EventPricing.of(dto, event);
        eventPricingRepository.save(eventPricing);
    }

}
