package com.startrip.codebase.util;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoogleGeocoderTest {

    Logger log = (Logger) LoggerFactory.getLogger(GoogleGeocoderTest.class);

    @DisplayName("주소에서 위도경도 추출")
    @Test
    public void test1() throws Exception {
        GoogleGeocodeApi googleGeocodeApi = new GoogleGeocodeApi();

        Map<String, String> test = new HashMap<>();
        test = googleGeocodeApi.getGeoDataByAddress("대전광역시 서구 둔산로123번길 15");

        for (String t : test.keySet()) log.info(">>>>" + t + " " + test.get(t));
    }
}