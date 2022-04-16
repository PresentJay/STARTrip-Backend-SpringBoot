package com.startrip.codebase.domain.event_review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event_review.dto.UpdateEventReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventReview {
    @Id
    @Column(name = "review_id")
    private UUID reviewId ;

    @Column(name = "creator_id")
    private UUID creatorId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    private String eventReviewTitle;

    private String text;

    private String reviewPicture;

    private Double reviewRate; //별점

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;


    public void update(UpdateEventReviewDto dto) {
        this.eventReviewTitle = dto.getEventReviewTitle();
        this.text = dto.getText();
        this.reviewRate =  dto.getReviewRate();
    }
}