package com.startrip.codebase.domain.openapi.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeightDto {

    private int popWeight;
    private int skyWeight;
    private int airWeight;
    private int sunRayWeight;

    public int totalWeightSum(){
        return popWeight + skyWeight + airWeight + skyWeight;
    }
}
