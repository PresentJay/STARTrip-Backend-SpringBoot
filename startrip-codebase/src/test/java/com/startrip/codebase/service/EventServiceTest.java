package com.startrip.codebase.service;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event_review.EventReview;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import com.startrip.codebase.domain.event_review.dto.CreateEventReviewDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EventServiceTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventReviewService eventReviewService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventReviewRepository eventReviewRepository;

<<<<<<< HEAD
    private void createEventReview(Event event){
        EventReview eventReview = EventReview.builder()
                .eventId(event)
                .eventReviewTitle("이벤트리뷰제목")
                .text("이벤트리뷰내용")
                .reviewRate(3.5)
                .build();

        eventReviewRepository.save(eventReview);
                }

    private void createEvent() {
        EventReview eventReview = EventReview.builder()
                .eventReviewTitle("이벤트리뷰제목")
                .text("이벤트리뷰내용")
                .reviewRate(3.5)
                .build();
        eventReviewRepository.save(eventReview);
    }

    @DisplayName("이벤트리뷰 등록 시 이벤트와 매핑된다.")
    @Test
    void eventReview_event(){
=======
    private Event createEvent(){
>>>>>>> d1603e46ab1cd289384ee8c7e6f9b854bf46a451
        Event event = Event.builder()
                .eventTitle("서울 롯데월드")
                .description("환상의 나라 에버랜드로~")
                .contact("02-123-421")
                .build();
<<<<<<< HEAD
        eventRepository.save(event);

        createEventReview(event);

        EventReview find = eventReviewService.getReviewEvent(1L);
        assertThat(find.getEventId().getEventId()).isEqualTo(0);
=======
        return eventRepository.save(event);
    }

    @DisplayName("Create")
    @Test
    void event_eventReview_create() {
        Event event = createEvent();

        CreateEventReviewDto dto = new CreateEventReviewDto();
        dto.setEventReviewTitle("롯데월드를 고발합니다.");
        dto.setText("사람이 너무 많아요");
        dto.setReviewRate(3.5);

        eventReviewService.createEventReview(dto);

        List<EventReview> reviews = eventReviewRepository.findByEventId(event.getEventId());

        assertThat(reviews.get(0).getEventReviewTitle()).isEqualTo("롯데월드를 고발합니다.");
    }

    @DisplayName("Read")
    @Test
    void getEventReview() {
        Event event = createEvent();
        for(int i = 0; i < 5; i++) {
            CreateEventReviewDto dto = new CreateEventReviewDto();
            dto.setEventId(event.getEventId());
            dto.setEventReviewTitle("Title" + i + " 번째 Review ");
            dto.setText("Text" + i + " 번째 Review ");
            dto.setReviewRate(3.5);
        }

        List<EventReview> reviews = eventReviewRepository.findByEventId(event.getEventId());

        for (EventReview review : reviews) {
            System.out.println(review.getEventReviewTitle());
            System.out.println(review.getText());
            System.out.println(review.getReviewRate());
        }
        assertThat(reviews.size()).isEqualTo(5);
>>>>>>> d1603e46ab1cd289384ee8c7e6f9b854bf46a451
    }

}
