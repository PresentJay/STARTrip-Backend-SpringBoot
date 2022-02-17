package com.startrip.codebase.domain.openapi.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AirPollutionInfo {

    private String informCode;
    private String informData;
    private String dataTime;
    private String informGrade;

}
