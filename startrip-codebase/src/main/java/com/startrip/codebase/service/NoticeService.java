package com.startrip.codebase.service;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.notice.NoticeRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.notice.NewNoticeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    private NoticeRepository noticeRepository;

    private UserRepository userRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<Notice> allNotice() {
        return noticeRepository.findAll();
    }

    @Transactional
    public Notice getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다"));

        viewCounting(notice);

        return notice;
    }

    public void createNotice(NewNoticeDto dto) {
        User user = userRepository.findByEmail(dto.getUserEmail())
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 없습니다."));

        Notice notice = Notice.of(dto, user, category);

        noticeRepository.save(notice);
    }

    public void updateNotice(Long id, NewNoticeDto dto){
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다"));

        notice.update(dto);
        noticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    private void viewCounting(Notice notice){
        notice.increaseViewCount();
        noticeRepository.save(notice); // 동시성
    }


}
