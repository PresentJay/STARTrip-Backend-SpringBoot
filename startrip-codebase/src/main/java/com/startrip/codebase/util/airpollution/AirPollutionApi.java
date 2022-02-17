package com.startrip.codebase.util.airpollution;

import com.startrip.codebase.domain.openapi.info.AirPollutionInfo;
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
public class AirPollutionApi {

    private final OpenApiUtil openApiUtil;

    public String getApiResult(String code) throws IOException {
        LinkedHashMap dateTime = openApiUtil.getDateTime("yyyy-MM-dd");
        ArrayList<LinkedHashMap> items = get(dateTime.get("date").toString(), code);
        List<AirPollutionInfo> airPollutionInfos = parse(items);
        Long score = Math.round(getAirResultScore(airPollutionInfos));
        log.info("대기질지수 : " + score);
        return score.toString();
    }

    private ArrayList<LinkedHashMap> get(String date, String code) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +"=" + openApiUtil.getDATA_GO_KR_API_KEY()); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("searchDate","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("informCode","UTF-8") + "=" + URLEncoder.encode(code, "UTF-8"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> apiResult = restTemplate.exchange(URI.create(urlBuilder.toString()), HttpMethod.GET, entity, Map.class);
        LinkedHashMap response = (LinkedHashMap) apiResult.getBody().get("response");
        LinkedHashMap body = (LinkedHashMap) response.get("body");
        ArrayList<LinkedHashMap> items = (ArrayList) body.get("items");

        return items;
    }

    private List<AirPollutionInfo> parse(ArrayList<LinkedHashMap> items) {
        List<AirPollutionInfo> airPollutionInfos = new ArrayList<>();

        // TODO : 현재 위치, 시간을 토대로 값을 가져오기
        for (LinkedHashMap item : items) {
            AirPollutionInfo info = new AirPollutionInfo();
            info.setInformCode(item.get("informCode").toString());
            info.setInformData(item.get("informData").toString());
            info.setInformGrade(item.get("informGrade").toString());
            info.setDataTime(item.get("dataTime").toString());
            airPollutionInfos.add(info);
        }
        return airPollutionInfos;
    }

    // 추출한 등급의 평군을 반환함
    private Double getAirResultScore(List<AirPollutionInfo> infos) {

        Double sumScore = 0.0;
        for (AirPollutionInfo info : infos) {
            String informGrade = info.getInformGrade();
            int findIndex = informGrade.indexOf("경남"); // TODO 현재 위치로 설정하기
            String gradeResult = informGrade.substring(findIndex, informGrade.indexOf(',', findIndex));
            sumScore += getScoreByGrade(gradeResult);

            //log.info(gradeResult);
        }
        sumScore /= infos.size();
        return sumScore;
    }

    private int getScoreByGrade(String grade) {
        if (grade.contains("좋음")){
            return 1;
        } else if (grade.contains("보통")) {
            return 2;
        } else if (grade.contains("나쁨")) {
            return 3;
        } else {
            return 4;
        }
    }
}
