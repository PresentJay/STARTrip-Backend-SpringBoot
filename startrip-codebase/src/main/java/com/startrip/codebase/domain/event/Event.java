package com.startrip.codebase.domain.event;

import com.startrip.codebase.dto.event.UpdateEventDto;
import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String eventTitle;

    private LocalDateTime startDate; // 시작 날짜

    private LocalDateTime endDate; // 끝 날짜

    public void update(UpdateEventDto dto) {
        this.eventTitle = dto.getEventTitle();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }
}
