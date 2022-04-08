package com.startrip.codebase.dto.operatingTime;


import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOptimeDto {

    private Long placeId;

    @ApiParam(value = "적용 시작 날짜", example = "YYYY-MM-DD")
    private String startDate;

    @ApiParam(value = "적용 마침 날짜", example = "YYYY-MM-DD")
    private String endDate;

    @ApiParam(value = "부분영업 시작 시간", example = "HH:mm:ss")
    private String startTime;

    @ApiParam(value = "부분영업 마침 시간", example = "HH:mm:ss")
    private String endTime;

    private Boolean isBreakTime;
    private Integer cycle;
}
