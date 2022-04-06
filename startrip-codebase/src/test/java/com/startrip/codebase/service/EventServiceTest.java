package com.startrip.codebase.service;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventServiceTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventReviewService eventReviewService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventReviewRepository eventReviewRepository;

    @DisplayName("이벤트 생성")
    @Test
    private Event createEvent(){
        Event event = Event.builder()
                .eventTitle()
                .description()
                .eventOwner()
                .build();
    }
}
