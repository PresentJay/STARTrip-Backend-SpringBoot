package com.startrip.codebase.domain.openapi.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LivingWeatherInfo {
    private String date;
    private String areaNo;
    private String today;
    private String tomorrow;
    private String dayAfterTomorrow;
    private String twoDayAfterTomorrow;
}
