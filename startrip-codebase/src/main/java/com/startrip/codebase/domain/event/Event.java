package com.startrip.codebase.domain.event;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Event {
    @Id
    private UUID event_id;

    @NotNull
    private Long creator_id;

    @NotNull
    private UUID place_id;

    @NotNull
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> category_list;

    @NotNull
    private String event_title;

    private String event_text;

    private String event_photo;

    private Date start_date;

    private Date end_date;

    private Date updated_date;

    private Boolean repeat;

    private String repeat_cycle;
}
