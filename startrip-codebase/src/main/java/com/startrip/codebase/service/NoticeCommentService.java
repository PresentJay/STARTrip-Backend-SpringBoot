package com.startrip.codebase.service;


import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.notice_comment.NoticeComment;
import com.startrip.codebase.domain.notice_comment.NoticeCommentRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;

@Service
public class NoticeCommentService {

    private NoticeCommentRepository noticeCommentRepository;
    private UserRepository userRepository;
    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeCommentService(NoticeCommentRepository noticeCommentRepository, UserRepository userRepository, NoticeRepository noticeRepository) {
        this.noticeCommentRepository = noticeCommentRepository;
        this.userRepository = userRepository;
        this.noticeRepository = noticeRepository;
    }

    public List<NoticeComment> getComments(Long noticeId) {
        List<NoticeComment> comments = noticeCommentRepository.findByNoticeId(noticeId);
        return comments;
    }

    public void newComment(NewCommentDto dto) {
        User writer = userRepository.findByEmail(dto.getUserEmail()).orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        });
        Notice notice = noticeRepository.findById(dto.getNoticeId()).orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 게시글입니다.");
        });

        NoticeComment comment = NoticeComment.of(dto, writer, notice);

        noticeCommentRepository.save(comment);
    }
}
