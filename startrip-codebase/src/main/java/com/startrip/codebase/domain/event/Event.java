package com.startrip.codebase.domain.event;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.event.dto.UpdateEventDto;
import com.startrip.codebase.domain.place.Place;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long eventId;

    /*
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "eventReview_id")
    private EventReview eventReview;
     */

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "place_id")
    private Place place;

    private String eventTitle;

    private String description;

    private LocalDateTime startDate; // 시작 날짜

    private LocalDateTime endDate; // 끝 날짜

    @Column(name = "event_cycle")
    private Integer eventCycle; //반복 주기

    @Column(name = "event_cycleUnit")
    private String eventCycleUnit; //반복 단위

    private String eventOwner; //이벤트 주최자

    private String contact;

    private LocalDateTime updatedDate;

    private LocalDateTime createdDate;

    private Time spendTime; //소요 시간

    private String spendTimeUnit; //소요 시간 단위

    private String eventPicture;

    public void update(UpdateEventDto dto) {
        this.eventTitle = dto.getEventTitle();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.description = dto.getDescription();
        this.eventCycle = dto.getEventCycle();
        this.eventCycleUnit = dto.getEventCycleUnit();
        this.contact = dto.getContact();
        this.eventPicture = dto.getEventPicture();
    }
}