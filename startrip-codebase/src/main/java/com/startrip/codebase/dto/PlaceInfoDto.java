package com.startrip.codebase.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PlaceInfoDto {

    @NotNull
    private Long placeId;

    private boolean isParkingLot;
    private boolean isEntranceFee;
    private boolean isRestRoom;

}
