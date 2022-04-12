package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.LoginDto;
import com.startrip.codebase.dto.PlaceDto;
import com.startrip.codebase.dto.PlaceInfoDto;
import com.startrip.codebase.service.PlaceService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaceInfoControllerTest {

    private final static Logger logger = LoggerFactory.getLogger(PlaceInfoControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataSource dataSource;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String userToken;
    private String adminToken;

    private final UUID randomPlaceId = UUID.randomUUID();
    private Long placeInfoId1;
    private Long placeInfoId2;
    private UUID placeId;

    @Autowired
    private PlaceService placeService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @BeforeAll
    public void dataSetUp() throws Exception {
        logger.info("테스트 용 데이터를 DB로 삽입합니다");
        try (Connection conn = dataSource.getConnection()) {  // (2)
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/data.sql"));  // (1)
        }
        createJwt();
    }

    @AfterAll
    public void dataCleanUp() throws SQLException {
        logger.info("테이블 초기화합니다");
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/scheme.sql"));
        }
    }

    private void createJwt() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("admin@admin.com");
        loginDto.setPassword("1234");

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        adminToken = mvcResult.getResponse().getContentAsString();

        loginDto = new LoginDto();
        loginDto.setEmail("user@user.com");
        loginDto.setPassword("1234");

        mvcResult = mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        userToken = mvcResult.getResponse().getContentAsString();
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("PlaceInfo에 새로운 정보를 추가한다 1")
    @Order(1)
    @Test
    void placeinfo_create_1() throws Exception {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setPlaceName("테스트 장소 1");
        placeDto.setAddress("경남 김해시 동동동");
        placeDto.setCategoryId(UUID.randomUUID());
        placeDto.setLatitude(33.3333);
        placeDto.setLongitude(122.2222);
        placeDto.setPlacePhoto("사진2");
        placeDto.setPhoneNumber("010-0000-0000");
        placeDto.setPlaceDescription("멋있는 장소이다.");
        placeId = placeService.createPlace(placeDto);

        PlaceInfoDto dto = new PlaceInfoDto();
        dto.setPlaceId(placeId);
        dto.setEntranceFee(true);
        dto.setParkingLot(true);
        dto.setParkingLot(true);

        MvcResult result = mockMvc.perform(
                        post("/api/place/info")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        placeInfoId1 = Long.valueOf(result.getResponse().getContentAsString());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("PlaceInfo에 새로운 정보를 추가한다 2")
    @Order(1)
    @Test
    void placeinfo_create_2() throws Exception {

        PlaceInfoDto dto = new PlaceInfoDto();
        dto.setPlaceId(placeId);
        dto.setEntranceFee(false);
        dto.setParkingLot(false);
        dto.setParkingLot(false);

        MvcResult result = mockMvc.perform(
                        post("/api/place/info")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        placeInfoId2 = Long.valueOf(result.getResponse().getContentAsString());
    }

    @DisplayName("PlaceInfo에 유저가 새로운 정보를 추가히면 403")
    @Order(2)
    @Test
    void placeinfo_create_3() throws Exception {

        PlaceInfoDto dto = new PlaceInfoDto();
        dto.setPlaceId(placeId);
        dto.setEntranceFee(false);
        dto.setParkingLot(false);
        dto.setParkingLot(false);

        mockMvc.perform(
                        post("/api/place/info")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("PlaceInfo의 정보를 수정한다")
    @Order(3)
    @Test
    void placeinfo_update() throws Exception {
        PlaceInfoDto dto = new PlaceInfoDto();
        dto.setPlaceId(placeId);
        dto.setEntranceFee(false);
        dto.setParkingLot(true);
        dto.setParkingLot(false);

        mockMvc.perform(
                        post("/api/place/info/" + placeInfoId1) // 수정하기 위해서는 생성했던 id와 같은 내용이어야 조회 후 수정 가능
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("PlaceInfo의 ID로 정보의 세부내역을 가져온다")
    @Order(4)
    @Test
    void placeinfo_detail_read() throws Exception {
        mockMvc.perform(get("/api/place/info/" + placeInfoId1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("PlaceInfo를 삭제한다")
    @Order(5)
    @Test
    void place_delete() throws Exception {
        mockMvc.perform(delete("/api/place/info/" + placeInfoId1)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("PlaceInfo의 삭제된 ID로 정보의 세부내역을 가져올 수 없다.")
    @Order(6)
    @Test
    void place_detail_read_1() throws Exception {
        mockMvc.perform(get("/api/place/info/" + placeInfoId1))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
