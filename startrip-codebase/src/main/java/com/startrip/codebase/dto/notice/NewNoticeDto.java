package com.startrip.codebase.dto.notice;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewNoticeDto {

    private String userEmail;

    private Long categoryId;

    @NotNull
    private String title;

    @NotNull
    private String text;

    private String attachment; // file url

}
