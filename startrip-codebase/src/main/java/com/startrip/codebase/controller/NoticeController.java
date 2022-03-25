package com.startrip.codebase.controller;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.dto.notice.NewNoticeDto;
import com.startrip.codebase.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api")
public class NoticeController {

    private NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
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
    ResponseEntity updateNotice(@PathVariable("id") Long id,@RequestBody NewNoticeDto dto) {
        noticeService.updateNotice(id, dto);
        return new ResponseEntity("수정되었습니다", HttpStatus.OK);
    }

    @DeleteMapping("/notice/{id}")
    public @ResponseBody
    ResponseEntity deleteNotice(@PathVariable("id") Long id) {
        noticeService.deleteNotice(id);
        return new ResponseEntity("삭제되었습니다", HttpStatus.OK);
    }
}
