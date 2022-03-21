package com.startrip.codebase.dto.event_trip;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class CreateEventTripDto {
    private UUID tripId;
    private Long userId;
    private String userPartner;
    private UUID eventId;
    private Date startTime;
    private Date endTime;
    private String state;
    private String transportation;
    private String title;
}
