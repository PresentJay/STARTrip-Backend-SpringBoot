package com.startrip.codebase.domain.notice;

import com.startrip.codebase.domain.user.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_id;

    @OneToOne(fetch = FetchType.LAZY)
    private User creator_id;

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
}
