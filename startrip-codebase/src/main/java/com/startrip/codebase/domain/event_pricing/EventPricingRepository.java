package com.startrip.codebase.domain.event_pricing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventPricingRepository extends JpaRepository<EventPricing, UUID> {

    public List<EventPricing> findAllByEvent_EventId(Long eventId);
}
