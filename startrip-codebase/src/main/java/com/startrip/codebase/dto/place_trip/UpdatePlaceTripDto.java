package com.startrip.codebase.dto.place_trip;

import com.startrip.codebase.domain.state.State;
import com.startrip.codebase.service.StateService;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class UpdatePlaceTripDto {
    private String userPartner;
    private UUID placeId;
    private java.util.Date startTime;
    private Date endTime;
    private State state;
    private String transportation;
    private String title;

//    public String placeTripState() {
//
//    }
}
