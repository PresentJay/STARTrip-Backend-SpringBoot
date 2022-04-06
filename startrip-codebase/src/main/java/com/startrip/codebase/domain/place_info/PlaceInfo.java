package com.startrip.codebase.domain.place_info;

import com.startrip.codebase.dto.PlaceInfoDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //자체 식별키

    @NotNull
    @Setter
    @Column(name = "place_id")
    private Long placeId;

    @Setter
    private boolean isParkingLot;

    @Setter
    private boolean isEntranceFee;

    @Setter
    private boolean isRestRoom;

    public static PlaceInfo of (PlaceInfoDto dto){
        return PlaceInfo.builder()
                .placeId(dto.getPlaceId())
                .isParkingLot(dto.isParkingLot())
                .isEntranceFee(dto.isEntranceFee())
                .isRestRoom(dto.isRestRoom())
                .build();
    }

    public void update(PlaceInfoDto dto) {
        this.isParkingLot = dto.isParkingLot();
        this.isEntranceFee = dto.isEntranceFee();
        this.isRestRoom = dto.isRestRoom();
    }
}