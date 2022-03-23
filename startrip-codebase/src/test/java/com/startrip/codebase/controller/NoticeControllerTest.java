package com.startrip.codebase.controller;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @DisplayName("게시글 생성 API 가 작동한다")
    @Test
    void notice_save() throws Exception {

        String jsonData = "{\"title\" : \"테스트제목\","
                + "\"text\" : \"내용\","
                + "\"attachment\" : \"파일URL\"}";

        mockMvc.perform(
                        post("/api/notice")
                                .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
                                .content(jsonData)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("생성되었습니다"))
                .andDo(print());
    }

    @DisplayName("게시글 조회 API 가 작동한다")
    @Test
    void notice_get() throws Exception {

        mockMvc.perform(
                        get("/api/notice")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }


}