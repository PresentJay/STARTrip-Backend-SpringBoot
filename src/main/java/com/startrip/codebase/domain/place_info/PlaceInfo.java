package com.startrip.codebase.domain.place_info;

import com.startrip.codebase.dto.PlaceInfoDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placeinfo_id")
    private Long id; //자체 식별키

    @NotNull
    @JoinColumn(name = "place_id")
    private UUID placeId;

    private boolean isParkingLot;

    private boolean isEntranceFee;

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