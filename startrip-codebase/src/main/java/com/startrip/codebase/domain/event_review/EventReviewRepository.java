package com.startrip.codebase.domain.event_review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
    public Optional<EventReview> findByEventReviewTitle(String eventReviewTitle);
}
