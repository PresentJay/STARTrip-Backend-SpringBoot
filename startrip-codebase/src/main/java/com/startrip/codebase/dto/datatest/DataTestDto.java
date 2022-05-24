package com.startrip.codebase.dto.datatest;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DataTestDto {
    private UUID testId;
    private String placeName;
    private double mapx;
    private double mapy;
    private String address;
}
