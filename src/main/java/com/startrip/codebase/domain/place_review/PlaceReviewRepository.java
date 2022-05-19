package com.startrip.codebase.domain.place_review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceReviewRepository extends JpaRepository<PlaceReview, UUID> {
}
