package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.dto.state.CreateStateDto;
import com.startrip.codebase.dto.state.UpdateStateDto;
import com.startrip.codebase.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StateControllerTest {
    public MockMvc mockMvc;

    private final StateService stateService;

    @Autowired
    public StateControllerTest(StateService stateService) {
        this.stateService = stateService;
    }

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StateController(stateService))
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 해결
                .build();
    }

    @DisplayName("Create 테스트")
    @Test
    public void test1() throws Exception {
        CreateStateDto dto = new CreateStateDto();
        dto.setStateID(UUID.fromString("b3142389-f12d-4c06-9d52-464cc2f55921"));
        dto.setStateNum(0);

        String objectMapper = new ObjectMapper().writeValueAsString(dto); // json 형식의 string 타입으로 변환
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/state")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Get 테스트")
    @Test
    public void test2() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/state/b3142389-f12d-4c06-9d52-464cc2f55921")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Update 테스트")
    @Test
    public void test3() throws Exception {
        UpdateStateDto dto = new UpdateStateDto();
        dto.setStateNum(0);

        String objectMapper = new ObjectMapper().writeValueAsString(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/state/b3142389-f12d-4c06-9d52-464cc2f55921")
                .contentType("application/json;charset=utf-8")
                .content("b3142389-f12d-4c06-9d52-464cc2f55921")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Update 후 Get 테스트")
    @Test
    public void test4() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/state/b3142389-f12d-4c06-9d52-464cc2f55921")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Delete 테스트")
    @Test
    public void test5() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/state/b3142389-f12d-4c06-9d52-464cc2f55921")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Delete 후 없는 데이터를 조회한다")
    @Test
    public void test6() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/state/e3661498-9473-4c06-9d52-464cc2f59429")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("없는 데이터입니다."))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
