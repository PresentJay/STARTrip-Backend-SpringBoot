package com.startrip.codebase.domain.notice;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Notice {
    @Id
    private UUID notice_id;

    @NotNull
    private Long creator_id;

    @NotNull
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> category_list;

    @NotNull
    private String notice_title;

    private String notice_text;

    private String notice_attachment;

    private Date created_date;

    private Date updated_date;

    private Integer like_count;

    private Integer view_count;
}
