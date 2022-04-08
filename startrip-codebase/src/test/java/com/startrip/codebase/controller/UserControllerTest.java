package com.startrip.codebase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.constant.Role;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.LoginDto;
import com.startrip.codebase.dto.SignUpDto;
import com.startrip.codebase.jwt.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenProvider tokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String jwtToken = "";

    @DisplayName("회원가입 API가 작동한다")
    @Test
    void test1() throws Exception {
        SignUpDto dto = new SignUpDto();
        dto.setEmail("test@test.com");
        dto.setPassword("1234");
        dto.setName("name");
        dto.setNickname("nickname");

        mockMvc.perform(
                        post("/api/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(dto.getEmail() + " 가입완료"))
                .andDo(print());

    }

    @DisplayName("로그인 API가 작동한다")
    @Test
    void test2() throws Exception {

        LoginDto dto = new LoginDto();
        dto.setEmail("test@test.com");
        dto.setPassword("1234");

        mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("로그인 후 JWT 토큰이 발급되고, 해당 토큰을 통해 인증 객체를 추출할 수 있다")
    @Test
    void test3() throws Exception {
        LoginDto dto = new LoginDto();
        dto.setEmail("test@test.com");
        dto.setPassword("1234");

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        jwtToken = mvcResult.getResponse().getContentAsString();

        Authentication authentication = tokenProvider.getAuthentication(jwtToken);
        assertThat(authentication.getName()).isEqualTo(dto.getEmail());
    }

    @DisplayName("일반 유저의 JWT 토큰을 통해 인증 객체를 추출하고, 권한을 확인할 수 있다")
    @Test
    void test4() throws Exception {
        LoginDto dto = new LoginDto();
        dto.setEmail("test@test.com");
        dto.setPassword("1234");

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        jwtToken = mvcResult.getResponse().getContentAsString();

        Authentication authentication = tokenProvider.getAuthentication(jwtToken);
        // 권한은 여러개를 가질 수 있으므로, stream 을 통해 추출한다
        assertThat(authentication.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .toString()).isEqualTo(Role.USER.getKey());
    }
}