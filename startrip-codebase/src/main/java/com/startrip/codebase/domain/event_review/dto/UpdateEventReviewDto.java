package com.startrip.codebase.domain.event_review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventReviewDto {
    private String reviewTitle;

    private String text;

    private String review_picture;

    private Double review_rate;
}
