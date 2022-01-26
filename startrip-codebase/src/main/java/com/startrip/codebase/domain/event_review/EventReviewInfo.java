package com.startrip.codebase.domain.event_review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventReviewInfo extends JpaRepository<EventReview, UUID> {
}
