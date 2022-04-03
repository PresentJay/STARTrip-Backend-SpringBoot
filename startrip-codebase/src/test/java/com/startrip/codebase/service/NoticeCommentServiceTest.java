package com.startrip.codebase.service;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.notice_comment.NoticeComment;
import com.startrip.codebase.domain.notice_comment.NoticeCommentRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeCommentServiceTest {

    @Autowired
    private NoticeCommentService commentService;

    @Autowired
    private NoticeCommentRepository commentRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    private Notice createNotice() {
        Notice notice = Notice.builder()
                .viewCount(0)
                .title("테스트 제목")
                .text("본문")
                .attachment("파일 URL")
                .build();

        return noticeRepository.save(notice);
    }

    private User createUser() {
        User user = User.builder()
                .name("성훈")
                .email("hoon@h.com")
                .password("1234")
                .nickname("훈")
                .build();
        return userRepository.save(user);
    }

    @DisplayName("해당 게시글에 코멘트가 생성된다")
    @Test
    void newComment() {
        Notice notice = createNotice();
        User user = createUser();

        NewCommentDto dto = new NewCommentDto();
        dto.setNoticeId(notice.getNoticeId());
        dto.setCommentText("댓글내용");
        dto.setUserEmail(user.getEmail());

        commentService.newComment(dto);

        List<NoticeComment> comments = commentRepository.findByNoticeId(notice.getNoticeId());

        assertThat(comments.get(0).getCommentText()).isEqualTo("댓글내용");
    }

    @DisplayName("해당 게시글에 10개의 코멘트가 생성되고 조회한다")
    @Test
    void getComment_and_10() {
        // given
        Notice notice = createNotice();
        User user = createUser();
        for (int i = 0 ; i < 10; i++){
            NewCommentDto dto = new NewCommentDto();
            dto.setNoticeId(notice.getNoticeId());
            dto.setCommentText("댓글내용"+i);
            dto.setUserEmail(user.getEmail());
            commentService.newComment(dto);
        }
        // when
        List<NoticeComment> comments = commentRepository.findByNoticeId(notice.getNoticeId());

        // then
        for (NoticeComment comment : comments) {
            System.out.println(comment.getCommentText());
        }
        assertThat(comments.size()).isEqualTo(10);
    }
}