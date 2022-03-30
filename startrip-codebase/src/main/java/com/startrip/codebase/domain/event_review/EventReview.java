package com.startrip.codebase.domain.event_review;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event_review.dto.UpdateEventReviewDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventReview {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long reviewId ;

    @Column(name = "creator_id")
    private Long creatorId;

    private String eventReviewTitle;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "event_id")
    private Event event;

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
