package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event.dto.CreateEventDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("Event Create TEST")
    @Order(1)
    @Test
    void event_create() throws Exception {
        CreateEventDto dto = new CreateEventDto();
        dto.setEventTitle("진해 구낭제");
        dto.setDescription("벗꼿페슽히벌");
        dto.setContact("010-1234-1234");

        mockMvc.perform(
                        post("/api/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Event Read TEST")
    @Order(2)
    @Test
    void event_read() throws Exception {

        mockMvc.perform(get("/api/event")).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Event Update TEST")
    @Order(3)
    @Test
    void event_update() throws Exception {

        CreateEventDto dto = new CreateEventDto();
        dto.setEventTitle("진해 구낭제");
        dto.setDescription("룰루랄라~");
        dto.setContact("010-1234-1234");

        mockMvc.perform(
                        post("/api/event/1") // 수정하기 위해서는 생성했던 id와 같은 내용이어야 조회 후 수정 가능
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Event Detail Read TEST")
    @Order(4)
    @Test
    void event_detail_read() throws Exception {
        mockMvc.perform(get("/api/event/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Event Delete TEST" )
    @Order(5)
    @Test
    void event_delete() throws Exception {
        mockMvc.perform(delete("/api/event/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}


