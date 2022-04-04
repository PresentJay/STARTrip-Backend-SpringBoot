package com.startrip.codebase.service;


import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.notice_comment.NoticeComment;
import com.startrip.codebase.domain.notice_comment.NoticeCommentRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import com.startrip.codebase.dto.noticecomment.ResponseCommentDto;
import com.startrip.codebase.dto.noticecomment.UpdateCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public List<ResponseCommentDto> getComment(Long noticeId) {
//        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
//            throw new IllegalStateException("존재하지 않는 게시글입니다.");
//        });
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            throw new IllegalStateException("조재하지 않는 게시글입니다.");
        });
        List<NoticeComment> comments = notice.getComments();
        List<ResponseCommentDto> dtos = new ArrayList<>();
        for (NoticeComment comment : comments) {
            dtos.add(ResponseCommentDto.of(comment));
        }
        return dtos;
    }

    @Transactional
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

    @Transactional
    public void updateComment(Long commentId, UpdateCommentDto dto) {
        NoticeComment comment = noticeCommentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new IllegalStateException("해당 댓글이 없습니다.");
                });
        comment.updateCommentText(dto.getCommentText());

        noticeCommentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        noticeCommentRepository.deleteById(commentId);
    }
}
