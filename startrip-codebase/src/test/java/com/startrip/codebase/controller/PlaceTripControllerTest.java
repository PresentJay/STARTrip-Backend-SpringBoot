package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.place_trip.PlaceTrip;
import com.startrip.codebase.domain.place_trip.PlaceTripRepository;
import com.startrip.codebase.service.trip.PlaceTripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.sql.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlaceTripControllerTest {
    public MockMvc mockMvc;

    private final PlaceTripService placeTripService;

    @Autowired
    public PlaceTripControllerTest(PlaceTripService placeTripService) {
        this.placeTripService = placeTripService;
    }

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PlaceTripController(placeTripService))
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 해결
                .alwaysExpect(status().isOk())
                .build();
    }

    @DisplayName("Create 테스트")
    @Test
    public void test1() throws Exception {
        PlaceTrip placeTrip = new PlaceTrip(
                UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429"),
                Long.valueOf("123"),
                "a",
                UUID.randomUUID(),
                Date.valueOf("2022-03-23"),
                Date.valueOf("2022-03-24"),
                "여행 계획",
                "버스",
                "울산 여행");

        String objectMapper = new ObjectMapper().writeValueAsString(placeTrip); // json 형식의 string 타입으로 변환
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/placetrip")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("All 테스트")
    @Test
    public void test2() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/placetrip")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Get 테스트")
    @Test
    public void test3() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Update 테스트")
    @Test
    public void test4() throws Exception {
        PlaceTrip placeTrip = new PlaceTrip(
                UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429"),
                Long.valueOf("123"),
                "b",
                UUID.randomUUID(),
                Date.valueOf("2022-03-25"),
                Date.valueOf("2022-03-30"),
                "여행 계획",
                "택시",
                "울산 여행");

        String objectMapper = new ObjectMapper().writeValueAsString(placeTrip); // json 형식의 string 타입으로 변환
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .contentType("application/json;charset=utf-8")
                .content("e3661498-9473-4c06-9d52-464cc2f59429")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Delete 테스트")
    @Test
    public void test5() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }
}
