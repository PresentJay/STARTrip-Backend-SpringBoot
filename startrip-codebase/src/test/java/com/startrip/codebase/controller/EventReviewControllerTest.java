package com.startrip.codebase.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event_review.EventReview;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import com.startrip.codebase.domain.event_review.dto.CreateEventReviewDto;
import com.startrip.codebase.domain.event_review.dto.UpdateEventReviewDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventReviewControllerTest {
    private final UUID EventId1 = UUID.randomUUID();
    private final UUID EventId2 = UUID.randomUUID();

    private UUID Id1;
    private UUID Id2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventReviewRepository eventReviewRepository;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper = new ObjectMapper();

    private EventReview target = null;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("EventReview Create TEST1")
    @Order(1)
    @Test
    void eventReview_create_1() throws Exception {
        Event event = Event.builder()
                .eventId(EventId1)
                .eventTitle("진해 군항제")
                .description("매해 4월1일 ~ 4월10일 진행된다.")
                .contact("010-1234-1231")
                .build();
        eventRepository.save(event);

        CreateEventReviewDto dto = new CreateEventReviewDto();
        dto.setEventId(EventId1);
        dto.setEventReviewTitle("구낭제요 !?!?!?!?");
        dto.setText("벚꽃이 굉장히 이쁘네요~");
        dto.setReviewRate(4.5);

        String objectMapper = new ObjectMapper().writeValueAsString(dto); // json 형식의 string 타입으로 변환
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/eventReview")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print()).andReturn();

        Id1 = UUID.fromString(mvcResult.getResponse()
                .getContentAsString()
                .replaceAll("\\\"",""));
    }

    @DisplayName("EventReview Create TEST2")
    @Order(2)
    @Test
    void eventReview_create_2() throws Exception {
        Event event = Event.builder()
                .eventId(EventId2)
                .eventTitle("진영 단감축제")
                .description("매해 11월 첫째주 금요일 ~ 일요일에 진행된다.")
                .contact("010-1234-1231")
                .build();
        eventRepository.save(event);

        Event find = eventRepository.findById(event.getEventId()).get();

        CreateEventReviewDto dto = new CreateEventReviewDto();
        dto.setEventId(find.getEventId());
        dto.setEventReviewTitle("B1A4 진영 단감축제 ㅋㅋ");
        dto.setText("단감은 싫어요 홍시가 좋아요");
        dto.setReviewRate(3.0);

        String objectMapper = new ObjectMapper().writeValueAsString(dto); // json 형식의 string 타입으로 변환
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/eventReview")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print()).andReturn();

        Id2 = UUID.fromString(mvcResult.getResponse()
                .getContentAsString()
                .replaceAll("\\\"",""));
    }

    @DisplayName("EventReview Read TEST")
    @Order(3)
    @Test
    void eventReview_read() throws Exception {
        mockMvc.perform(get("/api/eventReview"))
                .andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("EvenReview Update TEST")
    @Order(4)
    @Test
    void eventReview_Update() throws Exception {
        UpdateEventReviewDto dto = new UpdateEventReviewDto();
        dto.setEventId(EventId1);
        dto.setEventReviewTitle("진해 군항제");
        dto.setText("벚꽃이 만개 되지 않았어요");
        dto.setReviewRate(1.5);

        mockMvc.perform(
                        put("/api/eventReview/" + Id1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview Detail Read TEST")
    @Order(5)
    @Test
    void eventReview_detail_read() throws Exception {
        mockMvc.perform(get("/api/eventReview/" + Id2))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview Delete TEST" )
    @Order(6)
    @Test
    void eventReview_delete() throws Exception {
        mockMvc.perform(delete("/api/eventReview/" + Id1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("EventReview  Read After Delete TEST")
    @Order(7)
    @Test
    void evnetReview_Read_After_Delete() throws Exception {
        mockMvc.perform(get("/api/eventReview/" + Id1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("해당 이벤트 리뷰가 없습니다."))
                .andDo(print());
    }

    @DisplayName("EventReview Read TEST")
    @Order(8)
    @Test
    void eventReview_read_2() throws Exception {
        mockMvc.perform(get("/api/eventReview")).andExpect(status().isOk()).andDo(print());
    }
}
