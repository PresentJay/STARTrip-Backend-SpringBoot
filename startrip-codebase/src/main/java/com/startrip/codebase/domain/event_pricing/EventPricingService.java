package com.startrip.codebase.domain.event_pricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPricingService {

    private EventPricingRepository eventPricingRepository;

    @Autowired
    public EventPricingService(EventPricingRepository eventPricingRepository) {
        this.eventPricingRepository = eventPricingRepository;
    }

}
