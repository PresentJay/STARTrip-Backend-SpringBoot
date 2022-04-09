package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.event.dto.CreateEventDto;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import com.startrip.codebase.domain.event_review.dto.CreateEventReviewDto;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.PlaceDto;
import com.startrip.codebase.service.PlaceService;
import org.apache.logging.log4j.core.LifeCycle;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlaceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UUID category_id_sample = UUID.randomUUID();
    private UUID id1;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("Place에 새로운 장소를 추가한다 1")
    @Order(1)
    @Test
    void place_create_1() throws Exception {
        PlaceDto dto = new PlaceDto();
        dto.setPlaceName("테스트 장소 1");
        dto.setAddress("경남 김해시 동동동");
        dto.setCategoryId(category_id_sample);
        dto.setLatitude(33.3333);
        dto.setLongitude(122.2222);
        dto.setPlacePhoto("사진2");
        dto.setPhoneNumber("010-0000-0000");
        dto.setPlaceDescription("멋있는 장소이다.");

        MvcResult result = mockMvc.perform(
                        post("/api/place")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        id1 = UUID.fromString(result.getResponse()
                .getContentAsString()
                .replaceAll("\\\"",""));
    }

    @DisplayName("Place에 새로운 장소를 추가한다 2")
    @Order(2)
    @Test
    void place_create_2() throws Exception {
        PlaceDto dto = new PlaceDto();
        dto.setPlaceName("테스트 장소 2");
        dto.setAddress("서울시 종로구 동동동");
        dto.setCategoryId(category_id_sample);
        dto.setLatitude(33.4333);
        dto.setLongitude(122.3222);
        dto.setPlacePhoto("사진1");
        dto.setPhoneNumber("010-1234-5678");
        dto.setPlaceDescription("트랜디한 장소이다.");

        MvcResult result = mockMvc.perform(
                        post("/api/place")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("Place의 모든 목록을 읽어온다")
    @Order(3)
    @Test
    void place_list() throws Exception {
        mockMvc.perform(get("/api/place/list")).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Place의 장소를 수정한다")
    @Order(4)
    @Test
    void place_update() throws Exception {
        System.out.println(id1 + ", ");
        PlaceDto dto = new PlaceDto();
        dto.setPlaceName("테스트 장소 1");
        dto.setAddress("부산시 북구 동동동");
        dto.setCategoryId(category_id_sample);
        dto.setLatitude(33.3333);
        dto.setLongitude(122.2222);
        dto.setPlacePhoto("사진2");
        dto.setPhoneNumber("010-0000-0000");
        dto.setPlaceDescription("멋있는 장소이다.");

        mockMvc.perform(
                        post("/api/place/" + id1) // 수정하기 위해서는 생성했던 id와 같은 내용이어야 조회 후 수정 가능
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Place의 ID로 장소의 세부내역을 가져온다")
    @Order(5)
    @Test
    void place_detail_read() throws Exception {
        mockMvc.perform(get("/api/place/" + id1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Place를 삭제한다" )
    @Order(6)
    @Test
    void place_delete() throws Exception {
        mockMvc.perform(delete("/api/place/" + id1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Place의 삭제된 ID로 장소의 세부내역을 가져올 수 없다.")
    @Order(7)
    @Test
    void place_detail_read_1() throws Exception {
        mockMvc.perform(get("/api/place/" + id1))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("Place의 카테고리별 목록을 읽어온다")
    @Order(8)
    @Test
    void place_read_category() throws Exception {
        mockMvc.perform(get("/api/place/list/"+category_id_sample)).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Place의 모든 목록을 읽어온다")
    @Order(8)
    @Test
    void place_read_2() throws Exception {
        mockMvc.perform(get("/api/place/list")).andExpect(status().isOk()).andDo(print());
    }
}