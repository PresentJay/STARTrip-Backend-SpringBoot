package com.startrip.codebase.dto.operatingTime;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class ResponseOpTimeDto {
    private UUID placeId; // TODO: 체크필요, 이거 없애야 되는 게 아닐까?

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Boolean isBreakTime;
    private Integer cycle;

}
