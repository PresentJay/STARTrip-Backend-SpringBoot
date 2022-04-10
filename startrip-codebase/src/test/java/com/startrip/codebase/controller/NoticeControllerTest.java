package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.config.SecurityConfig;
import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.dto.LoginDto;
import com.startrip.codebase.dto.notice.NewNoticeDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NoticeControllerTest {

    private final static Logger logger = LoggerFactory.getLogger(NoticeControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    NoticeRepository noticeRepository;

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

    @AfterAll
    public void dataCleanUp() throws SQLException {
        logger.info("테이블 초기화합니다");
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/scheme.sql"));
        }
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("게시글 생성 API 가 작동한다")
    @Order(1)
    @Test
    void notice_save() throws Exception {

        Category category = Category.builder()
                .categoryParent(null)
                .categoryName("공지사항")
                .depth(0)
                .build();

        categoryRepository.save(category);

        Category find = categoryRepository.findCategoryByCategoryName("공지사항").get();

        NewNoticeDto dto = new NewNoticeDto();
        dto.setUserEmail("admin@admin.com");
        dto.setCategoryId(find.getId());
        dto.setAttachment("파일URL");
        dto.setText("본문");
        dto.setTitle("테스트제목");

        mockMvc.perform(
                        post("/api/notice")
                                .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", "Bearer " + adminToken)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("생성되었습니다"))
                .andDo(print());
    }

    @WithMockUser(roles = "USER")
    @DisplayName("일반 유저가 게시글 생성 API를 할시, 403")
    @Order(2)
    @Test
    void test0() throws Exception {

        Category find = categoryRepository.findCategoryByCategoryName("공지사항").get();

        NewNoticeDto dto = new NewNoticeDto();
        dto.setUserEmail("user@user.com");
        dto.setCategoryId(find.getId());
        dto.setAttachment("파일URL");
        dto.setText("본문");
        dto.setTitle("테스트제목");

        mockMvc.perform(
                        post("/api/notice")
                                .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("게시글 조회 API 가 작동한다")
    @Order(3)
    @Test
    void notice_get() throws Exception {

        mockMvc.perform(
                        get("/api/notice")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("게시글 수정 API 가 작동한다")
    @Order(4)
    @Test
    void notice_put() throws Exception {

        Notice notice = noticeRepository.findByTitle("테스트제목").get();

        NewNoticeDto dto = new NewNoticeDto();
        dto.setTitle("수정된제목");
        dto.setText("수정된내용");
        dto.setAttachment("수정된파일URL");

        mockMvc.perform(
                        put(String.format("/api/notice/%d", notice.getNoticeId()))
                                .header("Authorization", "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시글 수정 후, 상세 조회 API 가 작동한다")
    @Order(5)
    @Test
    void notice_detail_get() throws Exception {
        Notice notice = noticeRepository.findByTitle("수정된제목").get();

        mockMvc.perform(get(String.format("/api/notice/%d", notice.getNoticeId())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("게시글 삭제 API 가 작동한다")
    @Order(6)
    @Test
    void notice_delete() throws Exception {
        Notice notice = noticeRepository.findByTitle("수정된제목").get();

        mockMvc.perform(
                    delete(String.format("/api/notice/%d", notice.getNoticeId()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + adminToken)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시글 삭제후 조회 시 테스트")
    @Order(7)
    @Test
    void notice_get_and_delete() throws Exception {

        mockMvc.perform(
                        get("/api/notice/1")
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}