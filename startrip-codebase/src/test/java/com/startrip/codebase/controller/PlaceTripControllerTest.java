package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.place_trip.PlaceTrip;
import com.startrip.codebase.domain.place_trip.PlaceTripRepository;
import com.startrip.codebase.dto.place_trip.CreatePlaceTripDto;
import com.startrip.codebase.dto.place_trip.UpdatePlaceTripDto;
import com.startrip.codebase.service.trip.PlaceTripService;
import org.hibernate.sql.Update;
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
    public final PlaceTripRepository placeTripRepository;

    @Autowired
    public PlaceTripControllerTest(PlaceTripService placeTripService, PlaceTripRepository placeTripRepository) {
        this.placeTripService = placeTripService;
        this.placeTripRepository = placeTripRepository;
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
        CreatePlaceTripDto dto = new CreatePlaceTripDto();
        dto.setTripId(UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429"));
        dto.setUserId(Long.valueOf("123"));
        dto.setUserPartner("a");
        dto.setPlaceId(UUID.fromString("0f1e5a75-f3f4-4dbe-b739-e428e511e0e8"));
        dto.setStartTime(Date.valueOf("2022-03-23"));
        dto.setEndTime(Date.valueOf("2022-03-25"));
        dto.setState(null);
        dto.setTransportation("버스");
        dto.setTitle("울산 여행");

        String objectMapper = new ObjectMapper().writeValueAsString(dto); // json 형식의 string 타입으로 변환
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/placetrip")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Create 테스트")
    @Test
    public void test2() throws Exception {
        CreatePlaceTripDto dto = new CreatePlaceTripDto();
        dto.setTripId(UUID.fromString("a3661498-9473-4c06-9d52-464cc2f59429"));
        dto.setUserId(Long.valueOf("456"));
        dto.setUserPartner("c");
        dto.setPlaceId(UUID.fromString("1f1e5a75-f3f4-4dbe-b739-e428e511e0e8"));
        dto.setStartTime(Date.valueOf("2022-03-29"));
        dto.setEndTime(Date.valueOf("2022-03-30"));
        dto.setState(null);
        dto.setTransportation("기차");
        dto.setTitle("김해 여행");

        String objectMapper = new ObjectMapper().writeValueAsString(dto); // json 형식의 string 타입으로 변환
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/placetrip")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("All 테스트")
    @Test
    public void test3() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/placetrip")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Get 테스트")
    @Test
    public void test4() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Update 테스트")
    @Test
    public void test5() throws Exception {
        UpdatePlaceTripDto dto = new UpdatePlaceTripDto();
        dto.setUserPartner("b");
        dto.setPlaceId(UUID.fromString("0f1e5a75-f3f4-4dbe-b739-e428e511e0e8"));
        dto.setStartTime(Date.valueOf("2022-03-25"));
        dto.setEndTime(Date.valueOf("2022-03-26"));
        dto.setState(null);
        dto.setTransportation("택시");
        dto.setTitle("울산 여행");

        String objectMapper = new ObjectMapper().writeValueAsString(dto); // json 형식의 string 타입으로 변환
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .contentType("application/json;charset=utf-8")
                .content("e3661498-9473-4c06-9d52-464cc2f59429")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Update 후 Get 테스트")
    @Test
    public void test6() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Delete 테스트")
    @Test
    public void test7() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Delete 후 Get 테스트1")
    @Test
    public void test8() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/placetrip/e3661498-9473-4c06-9d52-464cc2f59429")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @DisplayName("Delete 후 Get 테스트2")
    @Test
    public void test9() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/placetrip/a3661498-9473-4c06-9d52-464cc2f59429")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }
}
