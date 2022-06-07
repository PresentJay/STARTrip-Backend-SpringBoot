package com.startrip.codebase.util.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.openapi.info.WeatherInfo;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherApi  {

    private final OpenApiUtil openApiUtil;

    public String getApiResult(String x, String y) throws IOException {
        LinkedHashMap dateTime = openApiUtil.getDateTime("yyyyMMdd");
        ArrayList<LinkedHashMap> items = get(x, y, dateTime.get("date").toString(), dateTime.get("time").toString());
        List<WeatherInfo> weatherList = parse(items);
        String score = getScore(weatherList);
        log.info("강수,하늘 평균값 : {}", score);
        return score;
    }

    private ArrayList<LinkedHashMap> get(String x, String y, String date, String time) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +"=" + openApiUtil.getDATA_GO_KR_API_KEY()); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(x, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(y, "UTF-8"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(URI.create(urlBuilder.toString()), HttpMethod.GET, entity, Map.class);

        ObjectMapper mapper = new ObjectMapper();
        // TODO : 최근 1일 조회 예외처리 잡기
        String jsonInString = mapper.writeValueAsString(response.getBody().get("response"));

        // 추출
        LinkedHashMap responseResult = (LinkedHashMap) ((LinkedHashMap) response.getBody().get("response")).get("body");
        LinkedHashMap bodyResult = (LinkedHashMap) responseResult.get("items");
        ArrayList<LinkedHashMap> items = (ArrayList<LinkedHashMap>) bodyResult.get("item");

        return items;
    }

    private List<WeatherInfo> parse(ArrayList<LinkedHashMap> items) {
        try {
            List<WeatherInfo> weatherInfo = new ArrayList<>();

            int i = 0;
            for (LinkedHashMap item : items) {
                String category = item.get("category").toString();
                if(category.equals("POP") || category.equals("SKY")){
                    WeatherInfo temp = new WeatherInfo();
                    temp.setCategory(item.get("category").toString());
                    temp.setFcstValue(Integer.parseInt(item.get("fcstValue").toString()));
                    temp.setBaseTime(item.get("baseTime").toString());
                    temp.setFcstTime(item.get("fcstTime").toString());
                    weatherInfo.add(temp);
                }
            }
            //log.info(weatherInfo.toString());
            return weatherInfo;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    private String getScore(List<WeatherInfo> weatherInfos) {
        // 평균값 추출
        int popSum = 0;
        int skySum = 0;
        int popSize = 0;
        int skySize = 0;

        for (WeatherInfo weatherInfo : weatherInfos) {
            String category = weatherInfo.getCategory();

            if (category.equals("POP")) {
                popSum += weatherInfo.getFcstValue();
                popSize++;
            } else{
                skySum += weatherInfo.getFcstValue();
                skySize++;
            }
        }

        int popAvg = popSum / popSize;
        int skyAvg = skySum / skySize;

        String result = String.valueOf(getScoreByPop(popAvg)) + ',' + String.valueOf(skyAvg);
        return result;
    }

    private int getScoreByPop(int pop) {
        if (pop >= 75){
            return 4;
        } else if (pop >= 50) {
            return 3;
        } else if (pop >= 25) {
            return 2;
        } else {
            return 1;
        }
    }
}
