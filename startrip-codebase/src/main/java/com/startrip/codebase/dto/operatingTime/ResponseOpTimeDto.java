package com.startrip.codebase.dto.operatingTime;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
public class ResponseOpTimeDto {
    private Long placeId; // TODO: 체크필요, 이거 없애야 되는 게 아닐까?

    private Date startDate;
    private Date endDate;
    private Timestamp startTime;
    private Timestamp endTime;

    private Boolean isBreakTime;
    private Integer cycle;

}
