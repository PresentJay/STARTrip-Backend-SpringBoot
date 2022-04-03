package com.startrip.codebase.controller;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice_comment.NoticeComment;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import com.startrip.codebase.dto.noticecomment.UpdateCommentDto;
import com.startrip.codebase.service.NoticeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoticeCommentController {
    private final NoticeCommentService noticeCommentService;

    @Autowired
    public NoticeCommentController(NoticeCommentService noticeCommentService) {
        this.noticeCommentService = noticeCommentService;
    }

    @GetMapping("/notice/{id}/comment")
    public @ResponseBody ResponseEntity getComment(@PathVariable("id") Long noticeId) {
        List<NoticeComment> comments;
        try {
            comments = noticeCommentService.getComment(noticeId);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(comments, HttpStatus.OK);
    }

    @PostMapping("/notice/{id}/comment")
    public ResponseEntity addComment(@PathVariable("id") Long noticeId, @RequestBody NewCommentDto dto) {
        try {
            dto.setNoticeId(noticeId);
            noticeCommentService.newComment(dto);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("댓글 생성되었습니다.", HttpStatus.CREATED);
    }

    // TODO : 수정 시, 해당 인증 정보를 받아서 처리하기 (SecurityHolder)
    @PutMapping("/notice/{id}/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable("commentId") Long commentId, @RequestBody UpdateCommentDto dto) {
        try {
            noticeCommentService.updateComment(commentId, dto);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("댓글 수정되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/notice/{id}/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        try {
            noticeCommentService.deleteComment(commentId);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("댓글 삭제되었습니다.", HttpStatus.OK);
    }
}
