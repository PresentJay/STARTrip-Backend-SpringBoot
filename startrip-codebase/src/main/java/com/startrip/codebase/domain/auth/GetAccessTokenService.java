package com.startrip.codebase.domain.auth;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GetAccessTokenService {
    public void getAccessToken(String authorizationCode) {
        try {
            AccessToken.getAccessToken(authorizationCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
