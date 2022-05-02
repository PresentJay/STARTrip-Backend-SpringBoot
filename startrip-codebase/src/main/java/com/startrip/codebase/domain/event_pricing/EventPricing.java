package com.startrip.codebase.domain.event_pricing;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event_pricing.dto.EventPricingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPricing {

    @Id
    @Column(name = "pricing_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    private String content; // 가격 정책

    private String range; // 가격 범위

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public static EventPricing of(EventPricingDto dto, Event event) {
        EventPricing eventPricing = EventPricing.builder()
                .id(UUID.randomUUID())
                .range(dto.getRange())
                .content(dto.getContent())
                .event(event)
                .createdTime(LocalDateTime.now())
                .build();
        return eventPricing;
    }
}
