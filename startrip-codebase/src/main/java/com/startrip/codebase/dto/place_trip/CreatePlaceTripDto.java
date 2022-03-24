package com.startrip.codebase.dto.place_trip;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CreatePlaceTripDto {
    private UUID tripId;
    private Long userId;
    private String userPartner;
    private UUID placeId;
    private Date startTime;
    private Date endTime;
    private String state;
    private String transportation;
    private String title;

    public String placeTripState() {
        Date time = new Date();
        String st = null;
        if(startTime.after(time)) st = "여행 계획";
        else if(startTime.before(time) && endTime.after(time)) st = "여행 상태";
        else if(endTime.before(time)) st = "여행 종료";
        return st;
    }
}
