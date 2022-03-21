package com.startrip.codebase.service;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> allNotice() {
        return noticeRepository.findAll();
    }

    public Notice getNotice(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isEmpty()) {
            throw new RuntimeException("해당 게시글이 없습니다");
        }
        return notice.get();
    }

    public void createNotice() {

    }

}
