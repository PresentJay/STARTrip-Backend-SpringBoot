package com.startrip.codebase.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoController {

    @Autowired
    private GetAccessTokenService service;

    @GetMapping("")
    public String main() {
        return "index";
    }

    @GetMapping("/oauth")
    public String getAuthCode(@RequestParam("code") String authorizationCode) {
        service.getAccessToken(authorizationCode);
        return "index";
    }
}