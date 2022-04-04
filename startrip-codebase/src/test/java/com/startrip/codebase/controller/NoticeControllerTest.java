package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.notice.NewNoticeDto;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    NoticeRepository noticeRepository;


    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper = new ObjectMapper();

    private void cleanUp() {
        noticeRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("게시글 생성 API 가 작동한다")
    @Order(1)
    @Test
    void notice_save() throws Exception {
        cleanUp();
        User user = User.builder()
                .name("테스트이름")
                .email("test@test.com")
                .password("1234")
                .nickname("nickname")
                .build();

        Category category = Category.builder()
                .categoryParent(null)
                .categoryName("공지사항")
                .depth(0)
                .build();

        userRepository.save(user);
        categoryRepository.save(category);

        Category find =categoryRepository.findCategoryByCategoryName("공지사항").get();

        NewNoticeDto dto = new NewNoticeDto();
        dto.setUserEmail("test@test.com");
        dto.setCategoryId(find.getId());
        dto.setAttachment("파일URL");
        dto.setText("본문");
        dto.setTitle("테스트제목");

        mockMvc.perform(
                        post("/api/notice")
                                .contentType(MediaType.APPLICATION_JSON) // JSON 타입으로 지정
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("생성되었습니다"))
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

    @DisplayName("게시글 삭제 API 가 작동한다")
    @Order(6)
    @Test
    void notice_delete() throws Exception {
        Notice notice = noticeRepository.findByTitle("수정된제목").get();

        mockMvc.perform(delete(String.format("/api/notice/%d", notice.getNoticeId())))
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