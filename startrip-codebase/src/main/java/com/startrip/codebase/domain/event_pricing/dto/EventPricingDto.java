package com.startrip.codebase.domain.event_pricing.dto;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event_pricing.EventPricing;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventPricingDto {
    private String content; // 가격 정책

    private String range; // 가격 범위

    public static EventPricingDto from(EventPricing eventPricing) {
        EventPricingDto dto = EventPricingDto.builder()
                .content(eventPricing.getContent())
                .range(eventPricing.getRange())
                .build();
        return dto;
    }
}
