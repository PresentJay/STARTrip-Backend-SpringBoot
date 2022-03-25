package com.startrip.codebase.service;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    private void createNotice(User user, Category category) {
        Notice notice = Notice.builder()
                .user(user)
                .category(category)
                .viewCount(0)
                .title("테스트 제목")
                .text("본문")
                .attachment("파일 URL")
                .build();

        noticeRepository.save(notice);
    }

    private void createNotice() {
        Notice notice = Notice.builder()
                .viewCount(0)
                .title("테스트 제목")
                .text("본문")
                .attachment("파일 URL")
                .build();

        noticeRepository.save(notice);
    }


    @DisplayName("게시글 조회 시, 조회수가 올라간다")
    @Test
    void notice_view_counting() {
        createNotice();

        Notice find = noticeService.getNotice(1L); // 게시글 조회 로직

        assertThat(find.getViewCount()).isEqualTo(1);
    }

    @DisplayName("게시글 100번 조회 테스트")
    @Test
    void notifce_view_100() {
        createNotice();

        for (int i = 0; i < 100; i++) {
            noticeService.getNotice(1L); // 게시글 조회 로직
        }

        Notice find = noticeRepository.findById(1L).get();
        assertThat(find.getViewCount()).isEqualTo(100);
    }

    @DisplayName("게시글 등록 시, 유저, 카테고리도 매핑된다")
    @Test
    void notice_post_with_user_and_category() {
        User user = User.builder()
                .name("테스트이름")
                .email("test@test.com")
                .build();

        Category category = Category.builder()
                .categoryParent(null)
                .categoryName("공지사항")
                .depth(0)
                .build();

        userRepository.save(user);
        categoryRepository.save(category);

        createNotice(user, category);

        Notice find = noticeService.getNotice(1L);

        assertThat(find.getUser().getEmail()).isEqualTo("test@test.com");
        assertThat(find.getCategory().getCategoryName()).isEqualTo("공지사항");
    }
}