package com.startrip.codebase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.notice_comment.NoticeComment;
import com.startrip.codebase.domain.notice_comment.NoticeCommentRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.notice.NewNoticeDto;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import com.startrip.codebase.dto.noticecomment.UpdateCommentDto;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class NoticeCommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeCommentRepository noticeCommentRepository;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Notice target = null;

    public void cleanUp() {
        noticeRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
        noticeCommentRepository.deleteAll();
    }
    
    private Notice createNotice() {
        User user = User.builder()
                .name("테스트")
                .email("test@test.com")
                .build();

        Category category = Category.builder()
                .categoryParent(null)
                .categoryName("공지사항")
                .depth(0)
                .build();

        Notice notice = Notice.builder()
                .title("게시글")
                .user(user)
                .category(category)
                .attachment("URL")
                .text("본문")
                .viewCount(0)
                .likeCount(0)
                .createdTime(LocalDateTime.now())
                .build();

        userRepository.save(user);
        categoryRepository.save(category);
        return noticeRepository.save(notice);
    }


    @DisplayName("게시글에 코멘트 생성 API가 작동한다")
    @Test
    void test1() throws Exception {
        cleanUp();
        // given
        createNotice();
        target = noticeRepository.findByTitle("게시글").get();

        NewCommentDto dto = new NewCommentDto();
        dto.setNoticeId(target.getNoticeId());
        dto.setCommentText("댓글내용");
        dto.setUserEmail("test@test.com");

        // when
        mockMvc.perform(post(String.format("/api/notice/%d/comment", dto.getNoticeId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("댓글 생성되었습니다."))
                .andDo(print());
    }

    @DisplayName("게시글에 코멘트 조회 API가 작동한다")
    @Test
    void test3() throws Exception {
        target = noticeRepository.findByTitle("게시글").get();

        // when
        mockMvc.perform(
                        get(String.format("/api/notice/%d/comment", target.getNoticeId()))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시글에 코멘트 수정 API가 작동한다")
    @Test
    void test4() throws Exception {
        target = noticeRepository.findByTitle("게시글").get();

        UpdateCommentDto dto = new UpdateCommentDto();
        dto.setCommentText("댓글수정");
        // when
        mockMvc.perform(
                        put(String.format("/api/notice/%d/comment/1", target.getNoticeId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }


    @DisplayName("게시글에 코멘트 삭제 API가 작동한다")
    @Test
    void test5() throws Exception {
        target = noticeRepository.findByTitle("게시글").get();

        // when
        mockMvc.perform(
                        delete(String.format("/api/notice/%d/comment/1",target.getNoticeId( )))
                )
                .andExpect(status().isOk())
                .andDo(print());
        cleanUp();
    }

}