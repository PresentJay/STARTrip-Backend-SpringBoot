package com.startrip.codebase.dto.operatingTime;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class UpdateOptimePeriodDto {

    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
}
