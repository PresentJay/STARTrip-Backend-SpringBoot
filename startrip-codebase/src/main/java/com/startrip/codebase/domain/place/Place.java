package com.startrip.codebase.domain.place;

import com.startrip.codebase.dto.PlaceDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private UUID id;

    @JoinColumn(name = "category_id")
    private UUID categoryId;

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

    @ElementCollection(targetClass = String.class)
    private List<String>  placeAnotherName;

    private String phoneNumber;

    public static Place of (PlaceDto dto){
        return Place.builder()
                .id(UUID.randomUUID())
                .placeName(dto.getPlaceName())
                .placeAnotherName(dto.getPlaceAnotherName())
                .address(dto.getAddress())
                .placePhoto(dto.getPlacePhoto())
                .categoryId(dto.getCategoryId())
                .placeDescription(dto.getPlaceDescription())
                .phoneNumber(dto.getPhoneNumber())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }

    public void update(PlaceDto dto) {
        this.categoryId = dto.getCategoryId();
        this.placeName = dto.getPlaceName();
        this.address = dto.getAddress();
        this.placePhoto = dto.getPlacePhoto();
        this.placeDescription = dto.getPlaceDescription();
        this.phoneNumber = dto.getPhoneNumber();
        this.placeAnotherName = dto.getPlaceAnotherName();
    }
}