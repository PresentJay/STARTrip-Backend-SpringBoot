package com.startrip.codebase.dto.place_trip;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class UpdatePlaceTripDto {
    private UUID tripId;
    private Long userId;
    private String userPartner;
    private UUID placeId;
    private Date startTime;
    private Date endTime;
    private String state;
    private String transportation;
    private String title;
}
