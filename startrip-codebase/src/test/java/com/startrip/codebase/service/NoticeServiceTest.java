package com.startrip.codebase.service;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        Notice notice = noticeRepository.findAll().get(0);
        Notice find = noticeService.getNotice(notice.getNoticeId()); // 게시글 조회 로직

        assertThat(find.getViewCount()).isEqualTo(1);
    }

    @DisplayName("게시글 100번 조회 테스트")
    @Test
    void notifce_view_100() {
        createNotice();

        List<Notice> notices = noticeRepository.findAll();
        Notice firstNotice = notices.get(0);
        for (int i = 0; i < 100; i++) {
            noticeService.getNotice(firstNotice.getNoticeId()); // 게시글 조회 로직
        }

        Notice find = noticeRepository.findById(firstNotice.getNoticeId()).get();
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
                .categoryName("공지사항")
                .depth(0)
                .build();

        userRepository.save(user);
        categoryRepository.save(category);

        createNotice(user, category);

        List<Notice> notices = noticeRepository.findAll();
        Notice firstNotice = notices.get(0);

        Notice find = noticeService.getNotice(firstNotice.getNoticeId());

        assertThat(find.getUser().getEmail()).isEqualTo("test@test.com");
        assertThat(find.getCategory().getCategoryName()).isEqualTo("공지사항");
    }
}