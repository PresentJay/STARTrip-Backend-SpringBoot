package com.startrip.codebase.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class PlaceInfoDto {

    @NotNull
    private UUID placeId;

    private boolean isParkingLot;
    private boolean isEntranceFee;
    private boolean isRestRoom;

}
