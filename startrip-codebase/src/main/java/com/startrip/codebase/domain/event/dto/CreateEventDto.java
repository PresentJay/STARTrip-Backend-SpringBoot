package com.startrip.codebase.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEventDto {

    private String eventTitle; // 이벤트 제목

    private String description;

    private String contact;

}
