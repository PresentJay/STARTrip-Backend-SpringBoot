package com.startrip.codebase.domain.notice;

import com.startrip.codebase.domain.category.Category;
import com.startrip.codebase.domain.notice_comment.NoticeComment;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.notice.NewNoticeDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Notice {

    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "notice", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<NoticeComment> comments;

    @NotNull
    private String title;

    @NotNull
    private String text;

    private String attachment; // file url

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Integer likeCount;

    private Integer viewCount;

    public static Notice of (NewNoticeDto dto, User user, Category category){
        Notice notice = Notice.builder()
                .user(user)
                .category(category)
                .title(dto.getTitle())
                .text(dto.getText())
                .attachment(dto.getAttachment())
                .createdTime(LocalDateTime.now())
                .likeCount(0)
                .viewCount(0)
                .build();
        return notice;
    }

    public void update(NewNoticeDto dto) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.attachment = dto.getAttachment();
        this.updatedTime = LocalDateTime.now();
    }

    public void increaseViewCount(){
        this.viewCount++;
    }

    public void addComment(NoticeComment comment) {
        this.comments.add(comment);
    }

}
