package com.startrip.codebase.domain.event.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
public class NewEventDto {

    private Long placeId;

    private Long categoryId;

    private String eventTitle; // 이벤트 제목

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Column(name = "event_cycle")
    private Integer eventCycle;

    @Column(name = "event_cycleUnit")
    private String eventCycleUnit;

    private String eventOwnew;

    private String contact;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private Time spendTime;

    private String spendTimeUnit;

    private String eventPicture;

}
