package com.startrip.codebase.curation.curationDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseLocationDto {

    private String placeName;
    private String placeAddress;
    private Double latitude;
    private Double longitude;
    private Double distance;
}