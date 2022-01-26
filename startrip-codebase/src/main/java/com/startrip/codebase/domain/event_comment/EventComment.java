package com.startrip.codebase.domain.event_comment;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.security.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class EventComment {
    @Id
    private Integer comment_id;

    @NotNull
    private Integer comment_up_id;

    @NotNull
    private Long user_id;

    @NotNull
    private UUID event_id;

    @NotNull
    private String comment_text;

    @NotNull
    private Timestamp created_time;

    private Boolean is_updated;
}
