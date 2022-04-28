package com.startrip.codebase.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEventDto {

    private UUID eventId;

    private String eventTitle; // 이벤트 제목

    private String description;

    private String contact;

}
