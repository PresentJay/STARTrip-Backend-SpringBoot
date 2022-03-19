package com.startrip.codebase.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateEventDto {

    private String eventTitle;

    private LocalDateTime startDate; // 시작 날짜

    private LocalDateTime endDate; // 끝 날짜

}
