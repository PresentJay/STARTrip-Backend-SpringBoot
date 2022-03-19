package com.startrip.codebase.dto.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseEventDto {

    private String eventTitle;

    private LocalDateTime startDate; // 시작 날짜

    private LocalDateTime endDate; // 끝 날짜

}
