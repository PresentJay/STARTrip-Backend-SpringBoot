package com.startrip.codebase.dto.event_trip;

import com.startrip.codebase.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CreateEventTripDto {
    private UUID tripId;
    private User userId;
    private String userPartner;
    private UUID eventId;
    private Date startTime;
    private Date endTime;
    private Integer state;
    private String transportation;
    private String title;
}
