package com.startrip.codebase.domain.event_trip;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventTripInfo extends JpaRepository<EventTrip, UUID> {
}
