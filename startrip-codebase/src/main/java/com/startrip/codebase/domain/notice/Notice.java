package com.startrip.codebase.domain.notice;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.notice.NewNoticeDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_id;

    @OneToOne(fetch = FetchType.LAZY)
    private User creator_id;

    // TODO : 값 타입 컬렉션 보단 관계 매핑으로 설계 고려
    @NotNull
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> category_list;

    @NotNull
    private String title;

    @NotNull
    private String text;

    private String attachment; // file url

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Integer like_count;

    private Integer view_count;

    public static Notice of (NewNoticeDto dto){
        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .attachment(dto.getAttachment())
                .createdTime(LocalDateTime.now())
                .like_count(0)
                .view_count(0)
                .build();
        return notice;
    }

    public void update(NewNoticeDto dto) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.attachment = dto.getAttachment();
        this.updatedTime = LocalDateTime.now();
    }
}
