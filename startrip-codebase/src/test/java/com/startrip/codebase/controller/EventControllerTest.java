package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event.dto.CreateEventDto;
import com.startrip.codebase.dto.LoginDto;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventControllerTest {
    private final static Logger logger = LoggerFactory.getLogger(PlaceTripControllerTest.class);

    private final UUID EventId1 = UUID.randomUUID();
    private final UUID EventId2 = UUID.randomUUID();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DataSource dataSource;

    private ObjectMapper objectMapper = new ObjectMapper();

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

    @DisplayName("Event Create TEST1")
    @Order(1)
    @Test
    void event_create_1() throws Exception {
        CreateEventDto dto = new CreateEventDto();
        dto.setEventId(EventId1);
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

    @DisplayName("Event Create TEST2")
    @Order(2)
    @Test
    void event_create_2() throws Exception {
        CreateEventDto dto = new CreateEventDto();
        dto.setEventId(EventId2);
        dto.setEventTitle("진영 단감축제");
        dto.setDescription("가을 축제");
        dto.setContact("test2@gmail.com");

        mockMvc.perform(
                        post("/api/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Event Read TEST")
    @Order(3)
    @Test
    void event_read() throws Exception {
        mockMvc.perform(get("/api/event"))
                .andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Event Update TEST")
    @Order(4)
    @Test
    void event_update() throws Exception {
        CreateEventDto dto = new CreateEventDto();
        dto.setEventId(EventId2);
        dto.setEventTitle("진영 단감축제");
        dto.setDescription("가을 축제");
        dto.setContact("010-1234-1234");

        mockMvc.perform(
                        put("/api/event/" + EventId2 )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Event Detail Read TEST")
    @Order(5)
    @Test
    void event_detail_read() throws Exception {
        mockMvc.perform(get("/api/event/" + EventId2))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Event Delete TEST" )
    @Order(6)
    @Test
    void event_delete() throws Exception {
        mockMvc.perform(delete("/api/event/" + EventId1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("Event Detail Read TEST")
    @Order(7)
    @Test
    void event_detail_read_1() throws Exception {
        mockMvc.perform(get("/api/event/" + EventId1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("해당 이벤트가 없습니다."))
                .andDo(print());
    }

    @DisplayName("Event Read TEST")
    @Order(8)
    @Test
    void event_read_2() throws Exception {
        mockMvc.perform(get("/api/event")).andExpect(status().isOk()).andDo(print());
    }
}


