package com.startrip.codebase.dto.operatingTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOptimeDto {

    private Long placeId;

    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    private Boolean isBreakTime;
    private Integer cycle;
}
