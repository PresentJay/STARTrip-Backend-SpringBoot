package com.startrip.codebase.dto.operatingTime;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class ResponseOptimeDto {

    private Long placeId; // TODO: 체크필요, 이거 없애야 되는 게 아닐까?

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Boolean isBreakTime;
    private Integer cycle;
}
