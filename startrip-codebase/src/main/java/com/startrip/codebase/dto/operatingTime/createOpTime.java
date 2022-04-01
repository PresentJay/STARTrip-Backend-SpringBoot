package com.startrip.codebase.dto.operatingTime;

import java.time.OffsetDateTime;
import java.util.Date;

public class createOpTime {
    private Long placeId;

    private OffsetDateTime starttimeWeekday;
    private OffsetDateTime endtimeWeekday;
    private OffsetDateTime starttimeWeekend;
    private OffsetDateTime endtimeWeekend;

    private Integer closedayofweek;
    private Integer closeDayscheduled;
    private Date closedaytemporary;
}
