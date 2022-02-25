package com.startrip.codebase.domain.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

@Slf4j
public class AccessToken {
    private static final String BASE_URL = "https://kauth.kakao.com";
    private static final String APP_KEY = "cf5763daccdfa2670e27aa192eee8d42";
    private static final String REDIRECT_URI = "http://localhost:8080/kakao/callback";

    private AccessToken() {}

    public static void getAccessToken(String authorizationCode) throws IOException {
        AccessToken client = new AccessToken();
        String urlWithParameters = makeHttpUrlWithParameters(authorizationCode);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(URI.create(urlWithParameters), HttpMethod.GET, entity, Map.class);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(response.getBody().get("response"));
        log.info(jsonInString);
    }

    private static Request makeRequest(HttpUrl url) {
        return new Request.Builder()
                .url(url)
                .build();
    }
    /*
     https://kauth.kakao.com/oauth/authorize
     ?client_id=cf5763daccdfa2670e27aa192eee8d42
     &redirect_uri=http://localhost:8080//kakao/callback
     &response_type=code

     */
    private static String makeHttpUrlWithParameters(String authorizationCode) throws MalformedURLException {
        StringBuilder uriBuilder = new StringBuilder(BASE_URL + "/oauth/token");

        uriBuilder.append("?:grant_type" + "=" + "authorization_code");
        uriBuilder.append("&" + "client_id" + "=" + APP_KEY);
        uriBuilder.append("&" + "redirect_uri" + "="+ REDIRECT_URI);
        uriBuilder.append("&" + "code" + "="+ authorizationCode);

        return uriBuilder.toString();
    }
}
