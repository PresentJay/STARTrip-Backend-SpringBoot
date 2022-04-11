package com.startrip.codebase.dto.operatingTime;


import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RequestOptimeDto {

    private UUID placeId;

    @ApiParam(value = "적용시작 날짜", example = "2000-01-01")
    private String startDate;

    @ApiParam(value = "적용종료 날짜", example = "2040-12-01")
    private String endDate;

    @ApiParam(value = "부분영업 시작 시간", example = "12:00:00")
    private String startTime;

    @ApiParam(value = "부분영업 종료 시간", example = "14:00:00")
    private String endTime;

    @ApiParam(value = "BreakTime 여부", example = "false")
    private Boolean isBreakTime;

    @ApiParam(value = "영업 주기", example = "1")
    private Integer cycle;
}
