package com.startrip.codebase.dto.operatingTime;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Setter
public class ResponseOptimeDto {

    private Long placeId; // TODO: 체크필요, 차라리 Place 이름을 보여줘야하지 않을까?

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Boolean isBreakTime;
    private Integer cycle;
}
