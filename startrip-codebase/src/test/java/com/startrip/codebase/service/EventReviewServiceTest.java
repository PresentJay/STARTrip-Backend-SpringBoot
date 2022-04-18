package com.startrip.codebase.service;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event_review.EventReview;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import com.startrip.codebase.domain.event_review.dto.CreateEventReviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EventReviewServiceTest {
    @Autowired
    private EventService eventService;

    @Autowired
    private EventReviewService eventReviewService;

    @Autowired
    private EventReviewRepository eventReviewRepository;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    public void setup() {
    }

    private Event createEvent() {
        Event event = Event.builder()
                .eventTitle("서울 롯데월드")
                .description("환상의 나라 에버랜드로~")
                .contact("02-123-421")
                .build();
        return eventRepository.save(event);
    }

    @DisplayName("해당 이벤트에 리뷰가 생성된다")
    @Order(1)
    @Test
    void createEventReview() {
        Event event = createEvent();
        CreateEventReviewDto dto = new CreateEventReviewDto();
        dto.setEventId(event.getEventId());
        dto.setEventReviewTitle("이벤트 리뷰 젬옥");
        dto.setText("이벤트 리뷰 텍스트");
        dto.setReviewRate(3.0);

        eventReviewService.createEventReview(dto);

        List<EventReview> eventReviews = eventReviewRepository.findAll();
        EventReview reviews = eventReviews.get(0);
        Event find = eventService.getEvent(reviews.getReviewId());

        assertThat(find.getEventTitle()).isEqualTo("서울 롯데월드");
    }

    @DisplayName("해당 이벤트에 5개의 이벤트리뷰가 생성되고 조회된다.")
    @Order(2)
    @Test
    void createEventReview_test(){
        Event event = createEvent();
        for(int i = 0; i < 5; i++) {
            CreateEventReviewDto dto = new CreateEventReviewDto();
            dto.setEventId(event.getEventId());
            dto.setEventReviewTitle( i+1 + "번째 이벤트 리뷰 제목");
            dto.setText(i + 1 + "번째 이벤트 리뷰 내용" );
            dto.setReviewRate(i + 1.0);
            eventReviewService.createEventReview(dto);
        }
        List<EventReview> eventReviews = eventReviewRepository.findAll();

        for(EventReview review : eventReviews){
            System.out.println(review.getEventReviewTitle());
        }
        assertThat(eventReviews.size()).isEqualTo(5);
    }

    @DisplayName("이벤트 삭제 시 리뷰가 모두 삭제된다.")
    @Order(3)
    @Test
    void eventReview_event_delete() {
        Event event = createEvent();
        EventReview  eventReview= EventReview.builder()
                .eventReviewTitle("이벤트리뷰제목")
                .text("이벤트리뷰내용")
                .reviewRate(3.5)
                .build();
        eventReviewRepository.save(eventReview);
        eventRepository.delete(event);

        List<EventReview> eventReviews = eventReviewRepository.findAll();
        EventReview reviews = eventReviews.get(0);
        Event find = eventService.getEvent(reviews.getReviewId());

        assertThat(reviews.getEventReviewTitle()).isEqualTo("이벤트리뷰제목");
        assertThat(find.getEventTitle()).isEqualTo("서울 롯데월드");
    }
}
