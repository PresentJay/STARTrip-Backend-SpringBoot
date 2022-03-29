package com.startrip.codebase.domain.place;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.dto.PlaceDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @Column(name = "place_id")
    private UUID placeId = UUID.randomUUID();

    @Column(name = "category_id")
    private UUID eventId = UUID.randomUUID();

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private String address;

    private String placePhoto;

    private String placeDescription;

    @NotNull
    private String placeName;

    private String phoneNumber;

    public static Place of (PlaceDto dto){
        Place place = Place.builder()
                .placeName(dto.getPlaceName())
                .address(dto.getAddress())
                .placePhoto(dto.getPlacePhoto())
                .placeDescription(dto.getPlaceDescription())
                .phoneNumber(dto.getPhoneNumber())
                // 임시 광화문 좌표값
                .latitude(37.576022)
                .longitude(126.9769)
                .build();
        return place;
    }

    public void update(PlaceDto dto) {
        this.placeName = dto.getPlaceName();
        this.address = dto.getAddress();
        this.placePhoto = dto.getPlacePhoto();
        this.placeDescription = dto.getPlaceDescription();
        this.phoneNumber = dto.getPhoneNumber();
    }
}
