package com.startrip.codebase.domain.event_review.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class CreateEventReviewDto {
    private UUID eventId;

    private String eventReviewTitle;

    private String text;

    private Double reviewRate;

}
