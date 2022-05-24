package com.startrip.codebase.domain.openapi.info;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeatherInfo {

    private String baseTime;

    private String fcstTime;

    private String category;
    // 날씨
    private int fcstValue;
}
