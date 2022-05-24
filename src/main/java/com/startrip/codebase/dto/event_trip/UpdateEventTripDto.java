package com.startrip.codebase.dto.event_trip;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class UpdateEventTripDto {
    private String userPartner;
    private UUID eventId;
    private Date startTime;
    private Date endTime;
    private Integer state;
    private String transportation;
    private String title;
}
