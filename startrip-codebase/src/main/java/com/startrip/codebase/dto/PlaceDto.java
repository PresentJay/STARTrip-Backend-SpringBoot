package com.startrip.codebase.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDto {

    @NotNull
    private String placeName;

    @NotNull
    private String address;

    private Integer categoryId;
    private String placeDescription;
    private String placePhoto;
    private String phoneNumber;

}
