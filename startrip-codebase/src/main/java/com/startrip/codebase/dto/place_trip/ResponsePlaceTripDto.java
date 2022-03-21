package com.startrip.codebase.dto.place_trip;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class ResponsePlaceTripDto {
    private UUID tripId;
    private UUID placeId;
    private String title;
}
