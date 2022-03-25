package com.startrip.codebase.service;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @DisplayName("게시글 조회 시, 조회수가 올라간다")
    @Test
    void notice_view_counting() {
        createNoticeAtDB();

        Notice find = noticeService.getNotice(1L); // 게시글 조회 로직

        assertThat(find.getView_count()).isEqualTo(1);
    }

    @DisplayName("게시글 100번 조회 테스트")
    @Test
    void notifce_view_100() {
        createNoticeAtDB();

        for (int i = 0; i < 100; i++){
            noticeService.getNotice(1L); // 게시글 조회 로직
        }

        Notice find = noticeRepository.findById(1L).get();
        assertThat(find.getView_count()).isEqualTo(100);
    }

    private void createNoticeAtDB() {
        Notice notice = Notice.builder()
                .view_count(0)
                .title("테스트 제목")
                .text("본문")
                .attachment("파일 URL")
                .build();
        noticeRepository.save(notice);
    }

}