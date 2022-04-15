package com.startrip.codebase.domain.event_review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
    List<EventReview> findByEventId(@Param(value = "event_id") Long eventId);
}
