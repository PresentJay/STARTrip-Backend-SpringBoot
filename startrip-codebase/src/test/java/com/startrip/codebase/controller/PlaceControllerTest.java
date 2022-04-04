package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.PlaceDto;
import com.startrip.codebase.service.PlaceService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlaceControllerTest {

    public MockMvc mockMvc;

    private final PlaceService placeService;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    public PlaceControllerTest(PlaceService placeService) {
        this.placeService = placeService;
    }

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PlaceController(placeService))
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 해결
                .build();
    }

    @After("")
    public void clean() {
        placeRepository.deleteAll();
    }

    @Test
    @DisplayName("장소 생성 API가 작동된다")
    public void createPlace() throws Exception {
        //given
        String placename = "테스트 장소";
        String address = "서울특별시 종로구 191";
        String photo = "https://www.naver.com/test.png";
        Long cateogoryid = 1L;
        String description = "테스트 장소 설명";
        String number = "010-1234-5678";
        Double latitude = 33.3333;
        Double longitude = 124.1111;

        Place placeDto = Place.builder()
                .placeName(placename)
                .address(address)
                .placePhoto(photo)
                .categoryId(cateogoryid)
                .placeDescription(description)
                .phoneNumber(number)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        String objectMapper = new ObjectMapper().writeValueAsString(placeDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/place")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

}
