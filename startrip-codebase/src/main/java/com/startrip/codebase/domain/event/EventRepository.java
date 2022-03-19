package com.startrip.codebase.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    public Optional<Event> findByEventTitle(String eventTitle);
}
