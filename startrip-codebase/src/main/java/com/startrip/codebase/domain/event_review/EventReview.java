package com.startrip.codebase.domain.event_review;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class EventReview {
    @Id
    private UUID review_id;

    @NotNull
    private Long creator_id;

    @NotNull
    private UUID event_id;

    @NotNull
    private String review_text;

    private String review_photo;

    @NotNull
    private Double review_star;

    @NotNull
    private Date created_date;

    private Date updated_date;
}
