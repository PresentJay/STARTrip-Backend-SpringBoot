package com.startrip.codebase.domain.place;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.dto.PlaceDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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

    private String phoneNumber;

    public static Place of (PlaceDto dto){
        return Place.builder()
                .placeName(dto.getPlaceName())
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
    }
}
