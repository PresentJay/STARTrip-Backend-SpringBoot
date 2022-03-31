package com.startrip.codebase.domain.place_info;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class PlaceInfo {

    @Id
    private Long placeId;

    private boolean isParkingLot;

    private boolean isEntranceFee;

    private boolean isRestRoom;

}