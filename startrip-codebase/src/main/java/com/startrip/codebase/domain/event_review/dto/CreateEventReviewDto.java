package com.startrip.codebase.domain.event_review.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CreateEventReviewDto {
    private Long creator_id;

    private Long event_id;

    private String eventTitle;

    private String text;

    private String review_picture;

    private Double review_rate;

    private LocalDateTime created_date;

    private LocalDateTime updated_date;

}
