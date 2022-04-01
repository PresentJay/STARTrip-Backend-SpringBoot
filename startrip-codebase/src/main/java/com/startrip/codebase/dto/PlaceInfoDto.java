package com.startrip.codebase.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceInfoDto {

    private Long placeId;
    private boolean isParkingLot;
    private boolean isEntranceFee;
    private boolean isRestRoom;

}
