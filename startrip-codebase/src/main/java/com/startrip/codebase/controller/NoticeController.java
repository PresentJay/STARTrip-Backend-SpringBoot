package com.startrip.codebase.controller;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice_comment.NoticeComment;
import com.startrip.codebase.dto.notice.NewNoticeDto;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import com.startrip.codebase.service.NoticeCommentService;
import com.startrip.codebase.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api")
public class NoticeController {

    private NoticeService noticeService;

    private NoticeCommentService noticeCommentService;

    @Autowired
    public NoticeController(NoticeService noticeService, NoticeCommentService noticeCommentService) {
        this.noticeService = noticeService;
        this.noticeCommentService = noticeCommentService;
    }

    @GetMapping("/notice")
    public @ResponseBody
    ResponseEntity
    allNotice() {
        List<Notice> notices = noticeService.allNotice();
        return new ResponseEntity(notices, HttpStatus.OK);
    }

    @GetMapping("/notice/{id}")
    public @ResponseBody
    ResponseEntity getNotice(@PathVariable("id") Long id) {
        Notice notice = noticeService.getNotice(id);
        return new ResponseEntity(notice, HttpStatus.OK);
    }

    @PostMapping("/notice")
    public @ResponseBody
    ResponseEntity addNotice(@RequestBody NewNoticeDto dto) {
        noticeService.createNotice(dto);
        return new ResponseEntity("생성되었습니다", HttpStatus.OK);
    }

    @PutMapping("/notice/{id}")
    public @ResponseBody
    ResponseEntity updateNotice(@PathVariable("id") Long id, @RequestBody NewNoticeDto dto) {
        noticeService.updateNotice(id, dto);
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @DeleteMapping("/notice/{id}")
    public @ResponseBody
    ResponseEntity deleteNotice(@PathVariable("id") Long id) {
        noticeService.deleteNotice(id);
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }

    // Notice Comment Controller
    @GetMapping("/notice/{id}/comment") // TODO : comment, 페이지네이션
    public ResponseEntity getComments(@PathVariable("id") Long noticeId) {
        List<NoticeComment> comments;
        try {
            comments = noticeCommentService.getComments(noticeId);
        } catch (Exception e) {
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
        return new ResponseEntity("댓글 생성되었습니다.", HttpStatus.OK);
    }
}
