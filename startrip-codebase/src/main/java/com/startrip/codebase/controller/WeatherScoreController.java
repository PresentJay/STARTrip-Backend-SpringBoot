package com.startrip.codebase.controller;


import com.startrip.codebase.domain.openapi.dto.WeightDto;
import com.startrip.codebase.util.OpenApiUtil;
import com.startrip.codebase.util.airpollution.AirPollutionApi;
import com.startrip.codebase.util.livingweather.LivingWeatherApi;
import com.startrip.codebase.util.weather.WeatherApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

@Slf4j
@RequestMapping("/api")
@Controller
public class WeatherScoreController {

    private final WeatherApi weatherApi;
    private final AirPollutionApi airPollutionApi;
    private final LivingWeatherApi livingWeatherApi;
    private final OpenApiUtil openApiUtil;

    @Autowired
    public WeatherScoreController(WeatherApi weatherApi, AirPollutionApi airPollutionApi, LivingWeatherApi livingWeatherApi, OpenApiUtil openApiUtil) {
        this.weatherApi = weatherApi;
        this.airPollutionApi = airPollutionApi;
        this.livingWeatherApi = livingWeatherApi;
        this.openApiUtil = openApiUtil;
    }

    @GetMapping("/weather")
    public @ResponseBody ResponseEntity getWeather() {
        try {
            // TODO: 조회 계산 시간 단축하기 , 현재 위치 좌표계로 넘기기
            String score = weatherApi.getApiResult("95", "77");
            return new ResponseEntity(score, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/air")
    public @ResponseBody
    ResponseEntity getAir() {
        try {
            String grades = airPollutionApi.getApiResult("PM10");
            return new ResponseEntity(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/livingweather")
    public @ResponseBody
    ResponseEntity getLivingWeather() {
        try {
            String grade = livingWeatherApi.getApiResult();
            return new ResponseEntity(grade, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/weatherscore")
    public @ResponseBody ResponseEntity getWeatherScore(@RequestBody WeightDto weightDto) throws IOException {
        long startTime = System.currentTimeMillis();
        try {
            log.info("날씨점수를 계산합니다...");
            String popSky = weatherApi.getApiResult("95", "77");
            double pop = Double.parseDouble(popSky.substring(0, popSky.indexOf(','))); // 반환 값 : 강소확률,하늘상태 (지수)
            double sky = Double.parseDouble(popSky.substring(popSky.indexOf(',') + 1)); // 반환 값 : 강소확률,하늘상태 (지수)
            double air = Double.parseDouble(airPollutionApi.getApiResult("PM10"));          // 반환 값 : 대기질 평균값 (지수)
            double sunRay = Double.parseDouble(livingWeatherApi.getApiResult()); // 가싱 지수 (1 ~ 4)
            double scoreResult = 0;

            scoreResult = (pop * weightDto.getPopWeight()
                    + sky * weightDto.getSkyWeight()
                    + air * weightDto.getAirWeight()
                    + sunRay * weightDto.getSunRayWeight()) / weightDto.totalWeightSum();
            int weatherScore = (int) Math.round(scoreResult);

            String grade = "";
            if (weatherScore == 1) {
                grade = "좋음";
            } else if (weatherScore == 2) {
                grade = "보통";
            } else if (weatherScore == 3) {
                grade = "나쁨";
            } else
                grade = "매우나쁨";
            log.info("날씨점수 결과입니다 {}, {}", weatherScore, grade);

            return new ResponseEntity(String.valueOf(weatherScore) + ',' + grade, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            long endTime = System.currentTimeMillis();
            long callTime = endTime - startTime;
            log.info("날씨점수 API 소요시간 ({}ms)", callTime);
        }
    }
}
