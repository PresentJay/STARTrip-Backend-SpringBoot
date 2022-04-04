package com.startrip.codebase.dto.event_trip;

import com.startrip.codebase.domain.state.State;
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
    private State state;
    private String transportation;
    private String title;
}
