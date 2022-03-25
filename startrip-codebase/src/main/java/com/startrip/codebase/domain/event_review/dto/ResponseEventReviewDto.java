package com.startrip.codebase.domain.event_review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEventReviewDto {
    private String eventReviewTitle;

    private String text;

    private Double review_rate;
}
