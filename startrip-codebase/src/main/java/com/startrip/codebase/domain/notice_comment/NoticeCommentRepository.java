package com.startrip.codebase.domain.notice_comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long> {

    @Query("select nc from NoticeComment nc where nc.notice.noticeId = :notice_id")
    List<NoticeComment> findByNoticeId(@Param(value = "notice_id") Long noticeId);
}
