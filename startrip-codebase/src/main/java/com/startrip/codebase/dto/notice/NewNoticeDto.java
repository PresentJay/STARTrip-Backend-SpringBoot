package com.startrip.codebase.dto.notice;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewNoticeDto {

    @NotNull
    private String title;

    @NotNull
    private String text;

    private String attachment; // file url

}
