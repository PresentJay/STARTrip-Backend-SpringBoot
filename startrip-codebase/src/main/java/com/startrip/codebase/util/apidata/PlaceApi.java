package com.startrip.codebase.util.apidata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.util.OpenApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlaceApi {
    @Autowired // 의존성 주입
    OpenApiUtil openApiUtil;

    public String getApiResult(String date) throws IOException, URISyntaxException {
        String items = get(date);
        store(items);
        return "ok";
    }

    public String get(String date) throws IOException, URISyntaxException {
        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/DataLabService/tmapTotalTarItsBroDDList");
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + openApiUtil.getDATA_GO_KR_API_KEY2());
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("baseYm", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(URI.create(urlBuilder.toString()), HttpMethod.GET, entity, Map.class);

        ObjectMapper mapper = new ObjectMapper();
        // TODO : 최근 1일 조회 예외처리 잡기
        String jsonInString = mapper.writeValueAsString(response.getBody().get("response"));

        // 추출
//        LinkedHashMap responseResult = (LinkedHashMap) ((LinkedHashMap) response.getBody().get("response")).get("body");
//        LinkedHashMap bodyResult = (LinkedHashMap) responseResult.get("items");
//        ArrayList<LinkedHashMap> items = (ArrayList<LinkedHashMap>) bodyResult.get("item");

//        return items;
        return jsonInString;
    }

    public List<String> store(String items) {
        try {
            File file = new File("./src/main/java/com/startrip/codebase/store/bigdata.xml");
            if (!file.exists()) {
                file.createNewFile(); // 파일 없으면 새로 생성
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(items));
            writer.flush();
            writer.close();

            return null;
        } catch (Exception e) {
            //log.info(e.getMessage());
            return null;
        }
    }
}
