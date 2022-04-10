package com.startrip.codebase.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("잘못된 접근 : {}", accessDeniedException);
        log.info("잘못된 접근 지역 메세지 : {}", accessDeniedException.getLocalizedMessage());
        log.info("잘못된 접근 메세지 : {}", accessDeniedException.getMessage());
        log.info("잘못된 접근 스택 트레이스 : {}", accessDeniedException.getStackTrace());

        request.setAttribute("errorMessage", accessDeniedException.getMessage());
    }
}
