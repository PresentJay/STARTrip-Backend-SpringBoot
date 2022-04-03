package com.startrip.codebase.domain.notice_comment;

import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.noticecomment.NewCommentDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class NoticeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    private Integer commentUpId; // TODO : 로직 고민

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @NotNull
    private String commentText;

    @NotNull
    private LocalDateTime createdTime;

    private Boolean isUpdated;

    public static NoticeComment of(NewCommentDto dto, User user, Notice notice) {

        NoticeComment comment = NoticeComment.builder()
                .user(user)
                .notice(notice)
                .commentText(dto.getCommentText())
                .createdTime(LocalDateTime.now())
                .build();

        return comment;
    }

    public void updateCommentText(String commentText) {
        this.commentText = commentText;
    }
}
