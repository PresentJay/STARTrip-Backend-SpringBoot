package com.startrip.codebase.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
@Getter
@ConfigurationProperties("")
public class OpenApiUtil {

    @Value("${datago.api_key}")
    private String DATA_GO_KR_API_KEY;

    @Value("${datago.api_key2}")
    private String DATA_GO_KR_API_KEY2;


    // 기준시간을 기준으로 전날 데이터를 가져올지 결정하는 메소드
    public LinkedHashMap getDateTime(String datePattern){
        // 기준 시간 (0500 부터 예보조회가 가능함)
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(5, 0, 0));
        // 현재 시각
        LocalDateTime nowDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        String date, time;
        if (startDateTime.isBefore(nowDateTime)){
            // 오늘 0500 이전이면 전날 최신 데이터를 가져온다
            date = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(23, 0, 0))
                    .format(DateTimeFormatter.ofPattern(datePattern));
            time = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(23, 0, 0))
                    .format(DateTimeFormatter.ofPattern("HHmm"));
        } else {
            // 오늘 0500 이후이면 0500 데이터를 가져온다
            date = LocalDateTime.of(LocalDate.now(), LocalTime.of(05, 0, 0))
                    .format(DateTimeFormatter.ofPattern(datePattern));
            time = LocalDateTime.of(LocalDate.now(), LocalTime.of(05, 0, 0))
                    .format(DateTimeFormatter.ofPattern("HHmm"));
        }
        log.info("시각: " + date + ", " + time);
        LinkedHashMap result = new LinkedHashMap<>();
        result.put("date", date);
        result.put("time", time);
        return result;
    }

}
