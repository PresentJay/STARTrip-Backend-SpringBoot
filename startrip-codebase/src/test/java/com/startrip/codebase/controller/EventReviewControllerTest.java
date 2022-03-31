package com.startrip.codebase.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import com.startrip.codebase.domain.event_review.dto.CreateEventReviewDto;
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
public class EventReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventReviewRepository eventReviewRepository;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("EventReview Create TEST")
    @Order(1)
    @Test
    void eventReview_create_1() throws Exception {
        CreateEventReviewDto dto = new CreateEventReviewDto();
        dto.setEventReviewTitle("진해 군항제");
        dto.setText("벚꽃이 굉장히 이쁘네요~");
        dto.setReviewRate(4.5);

        mockMvc.perform(
                        post("/api/eventReview")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview Create TEST")
    @Order(2)
    @Test
    void eventReview_create_2() throws Exception {
        CreateEventReviewDto dto = new CreateEventReviewDto();
        dto.setEventReviewTitle("B1A4 진영 단감축제 ㅋㅋ");
        dto.setText("단감은 싫어요 홍시가 좋아요");
        dto.setReviewRate(3.0);

        mockMvc.perform(
                        post("/api/eventReview")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview Read TEST")
    @Order(3)
    @Test
    void eventReview_read() throws Exception {

        mockMvc.perform(get("/api/eventReview")).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("EvenReview Update TEST")
    @Order(4)
    @Test
    void eventReview_Update() throws Exception {

        CreateEventReviewDto dto = new CreateEventReviewDto();
        dto.setEventReviewTitle("진해 군항제");
        dto.setText("벚꽃이 만개 되지 않았어요");
        dto.setReviewRate(1.5);

        mockMvc.perform(
                        post("/api/eventReview/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview Detail Read TEST")
    @Order(5)
    @Test
    void eventReview_detail_read() throws Exception {
        mockMvc.perform(get("/api/eventReview/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview Delete TEST" )
    @Order(6)
    @Test
    void eventReview_delete() throws Exception {
        mockMvc.perform(delete("/api/eventReview/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview  Read After Delete TEST")
    @Order(7)
    @Test
    void evnetReview_Read_After_Delete() throws Exception {
        mockMvc.perform(get("/api/eventReview")).andExpect(status().isOk()).andDo(print());
    }
}
