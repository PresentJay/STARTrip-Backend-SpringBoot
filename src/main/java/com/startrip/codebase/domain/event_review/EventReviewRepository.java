package com.startrip.codebase.domain.event_review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventReviewRepository extends JpaRepository<EventReview, UUID> {

    @Query("select er from EventReview er where er.event.eventId = :event_id ")
    List<EventReview> findByEventId(@Param(value = "event_id") UUID eventId);
    Optional<EventReview> findByEventReviewTitle(String eventReviewTitle);
    List<EventReview> findByEvent_EventId(UUID eventId);
}
