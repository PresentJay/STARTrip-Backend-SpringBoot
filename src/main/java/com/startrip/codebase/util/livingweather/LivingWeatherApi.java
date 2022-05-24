package com.startrip.codebase.util.livingweather;

import com.startrip.codebase.domain.openapi.info.LivingWeatherInfo;
import com.startrip.codebase.util.OpenApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class LivingWeatherApi {

    private final OpenApiUtil openApiUtil;

    public String getApiResult() throws IOException {
        // TODO : 지역 번호 받기
        LinkedHashMap dateTime = openApiUtil.getDateTime("yyyyMMdd");
        String date = dateTime.get("date").toString() + dateTime.get("time").toString().substring(0, 2);
        LinkedHashMap item = get(date);
        LivingWeatherInfo info = parse(item);
        String score = getScore(info);
        log.info("자외선지수 : " + String.valueOf(score) + " 등급 : " + info.getToday());
        return score;
    }

    private LinkedHashMap get(String date) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/LivingWthrIdxServiceV2/getUVIdxV2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +"=" + openApiUtil.getDATA_GO_KR_API_KEY()); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> apiResult = restTemplate.exchange(URI.create(urlBuilder.toString()), HttpMethod.GET, entity, Map.class);
        LinkedHashMap response = (LinkedHashMap) apiResult.getBody().get("response");
        LinkedHashMap body = (LinkedHashMap) response.get("body");
        LinkedHashMap items = (LinkedHashMap) body.get("items");

        return items;
    }

    private LivingWeatherInfo parse(LinkedHashMap items) {
        LinkedHashMap item = (LinkedHashMap) ((ArrayList) items.get("item")).get(0);
        LivingWeatherInfo info = new LivingWeatherInfo();
        info.setDate(item.get("date").toString());
        info.setAreaNo(item.get("areaNo").toString());
        info.setToday(item.get("today").toString());
        info.setTomorrow(item.get("tomorrow").toString());
        info.setDayAfterTomorrow(item.get("dayaftertomorrow").toString());
        info.setTwoDayAfterTomorrow(item.get("twodaysaftertomorrow").toString());
        // TODO 야간에는 today 가 공백임 예외처리 하기
        if (info.getToday().isBlank()) {
            info.setToday(info.getTomorrow());
        }
        return info;
    }

    private String getScore(LivingWeatherInfo info){
        int result = getGradeByScore(Integer.parseInt(info.getToday()));
        return String.valueOf(result);
    }

    private int getGradeByScore(int grade) {
        // 자외선 범위
        if (grade >= 8) {
            return 4;
        } else if (grade >= 6) {
            return 3;
        } else if (grade >= 3) {
            return 2;
        } else
            return 1;
    }
}
