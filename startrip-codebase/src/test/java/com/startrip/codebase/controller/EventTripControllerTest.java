package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.LoginDto;
import com.startrip.codebase.dto.event_trip.CreateEventTripDto;
import com.startrip.codebase.dto.event_trip.UpdateEventTripDto;
import com.startrip.codebase.service.trip.EventTripService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // id1, id2가 삭제되는 것을 막음
public class EventTripControllerTest {
    private final static Logger logger = LoggerFactory.getLogger(NoticeControllerTest.class);

    @Autowired
    public MockMvc mockMvc;

    private final UUID eventTripId1 = UUID.randomUUID();
    private final UUID eventTripId2 = UUID.randomUUID();

    private UUID id1;
    private UUID id2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataSource dataSource;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String userToken;
    private String adminToken;

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
        // 관리자 토큰 발급
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

        // 일반 유저 토큰 발급
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

    public void cleanUp() {
        userRepository.deleteAllInBatch();
    }

    @WithMockUser(roles = "USER")
    @DisplayName("Event Trip Create 테스트 1번")
    @Test
    public void test1() throws Exception {
        cleanUp();

        User user = User.builder()
                .name("b")
                .email("user@user.com")
                .build();
        userRepository.save(user);

        CreateEventTripDto dto = new CreateEventTripDto();
        dto.setTripId(eventTripId1);
        dto.setUserId(user);
        dto.setUserPartner("a");
        dto.setEventId(UUID.randomUUID());
        dto.setStartTime(Date.valueOf("2022-03-23"));
        dto.setEndTime(Date.valueOf("2022-03-25"));
        dto.setState(1);
        dto.setTransportation("버스");
        dto.setTitle("울산 여행");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/eventtrip")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper.writeValueAsString(dto)) // json 형식의 string 타입으로 변환
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)
        ).andExpect(status().isOk()).andDo(print()).andReturn();

        id1 = UUID.fromString(mvcResult.getResponse()
                .getContentAsString()
                .replaceAll("\\\"",""));
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("Event Trip Create 테스트 2번")
    @Test
    public void test2() throws Exception {
        User user = User.builder()
                .name("b")
                .email("admin@admin.com")
                .build();
        userRepository.save(user);

        CreateEventTripDto dto = new CreateEventTripDto();
        dto.setTripId(eventTripId2);
        dto.setUserId(user);
        dto.setUserPartner("c");
        dto.setEventId(UUID.randomUUID());
        dto.setStartTime(Date.valueOf("2022-03-29"));
        dto.setEndTime(Date.valueOf("2022-03-30"));
        dto.setState(1);
        dto.setTransportation("기차");
        dto.setTitle("김해 여행");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/eventtrip")
                .contentType("application/json;charset=utf-8")
                .content(objectMapper.writeValueAsString(dto)) // json 형식의 string 타입으로 변환
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + adminToken)
        ).andExpect(status().isOk()).andDo(print()).andReturn();

        id2 = UUID.fromString(mvcResult.getResponse()
                .getContentAsString()
                .replaceAll("\\\"",""));
    }

    @DisplayName("All 테스트")
    @Test
    public void test3() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/eventtrip")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Get 테스트")
    @Test
    public void test4() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/eventtrip/" + id1)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @WithMockUser(roles = "USER")
    @DisplayName("Update 테스트")
    @Test
    public void test5() throws Exception {
        UpdateEventTripDto dto = new UpdateEventTripDto();
        dto.setUserPartner("b");
        dto.setEventId(UUID.randomUUID());
        dto.setStartTime(Date.valueOf("2022-03-25"));
        dto.setEndTime(Date.valueOf("2022-03-26"));
        dto.setTransportation("택시");
        dto.setTitle("울산 여행");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/eventtrip/" + id1)
                .contentType("application/json;charset=utf-8")
                .content(objectMapper.writeValueAsString(dto)) // json 형식의 string 타입으로 변환
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Update 후 Get 테스트")
    @Test
    public void test6() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/eventtrip/" + id1)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @WithMockUser(roles = "USER")
    @DisplayName("Delete 테스트")
    @Test
    public void test7() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/eventtrip/" + id1)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)
        ).andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Delete 후 없는 데이터를 조회한다") //  지운 데이터 조회
    @Test
    public void test8() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/eventtrip/" + id1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("없는 데이터입니다."))
                .andDo(print());
    }

    @DisplayName("Delete 후 있는 데이터를 조회한다") // 지우지 않은 데이터 조회
    @Test
    public void test9() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/eventtrip/" + id2)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
        cleanUp();
    }
}
