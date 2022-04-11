package com.startrip.codebase.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlaceDto {

    @NotNull
    private String placeName;

    @NotNull
    private String address;

    private UUID categoryId;
    private String placeDescription;
    private String placePhoto;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;

}
