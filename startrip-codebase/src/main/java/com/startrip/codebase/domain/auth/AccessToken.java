package com.startrip.codebase.domain.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

@Slf4j
public class AccessToken {
    private static @Value("${client.client_id}") String APP_KEY;
    private static @Value("${client.token}") String TOKEN_URL;
    private static @Value("${client.redirect-uri}") String REDIRECT_URI ;

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

    private static String makeHttpUrlWithParameters(String authorizationCode) throws MalformedURLException {
        StringBuilder uriBuilder = new StringBuilder(TOKEN_URL);

        uriBuilder.append("?:grant_type" + "=" + "authorization_code");
        uriBuilder.append("&" + "client_id" + "=" + APP_KEY);
        uriBuilder.append("&" + "redirect_uri" + "="+ REDIRECT_URI);
        uriBuilder.append("&" + "code" + "="+ authorizationCode);

        return uriBuilder.toString();
    }
}
