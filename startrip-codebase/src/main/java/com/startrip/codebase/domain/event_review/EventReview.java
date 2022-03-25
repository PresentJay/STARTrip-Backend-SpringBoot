package com.startrip.codebase.domain.event_review;

import com.startrip.codebase.domain.event_review.dto.UpdateEventReviewDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventReview {
    @Id
    private UUID review_id;

    @NotNull
    private Long creator_id;

    @NotNull
    private String reviewTitle;

    @NotNull
    private Long event_id;

    @NotNull
    private String text;

    private String review_picture;

    @NotNull
    private Double review_rate;

    @NotNull
    private LocalDateTime created_date;

    private LocalDateTime updated_date;


    public void update(UpdateEventReviewDto dto) {
        this.reviewTitle = dto.getReviewTitle();
        this.text = dto.getText();
        this.review_picture = dto.getReview_picture();
        this.review_rate = dto.getReview_rate();
    }
}
