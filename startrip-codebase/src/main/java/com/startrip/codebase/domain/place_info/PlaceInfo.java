package com.startrip.codebase.domain.place_info;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class PlaceInfo {

    @Id
    private UUID placeId;

    private boolean isParkingLot;

    private boolean isEntranceFee;

    private boolean isRestRoom;

}