package com.startrip.codebase.service;

import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event_review.EventReview;
import com.startrip.codebase.domain.event_review.EventReviewRepository;
import com.startrip.codebase.domain.event_review.dto.CreateEventReviewDto;
import com.startrip.codebase.domain.event_review.dto.ResponseEventReviewDto;
import com.startrip.codebase.domain.event_review.dto.UpdateEventReviewDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventReviewService {
    private EventReviewRepository eventReviewRepository;
    private EventRepository eventRepository;

    @Autowired
    public EventReviewService(EventReviewRepository eventReviewRepository, EventRepository eventRepository) {
        this.eventReviewRepository = eventReviewRepository;
        this.eventRepository = eventRepository;
    }


    public void createEventReview(CreateEventReviewDto dto) {
        EventReview eventReview = EventReview.builder()
                .eventReviewTitle(dto.getEventReviewTitle())
                .text((dto.getText()))
                .reviewRate((dto.getReviewRate()))
                .build();
        eventReviewRepository.save((eventReview));
        log.info(eventReview.toString());
    }

    public List<ResponseEventReviewDto> allEventReview() {
        List<EventReview> eventReviews = eventReviewRepository.findAll();
        List<ResponseEventReviewDto> dtos = new ArrayList<>();

        for (EventReview eventReview : eventReviews) {
            ResponseEventReviewDto dto = new ResponseEventReviewDto();
            dto.setEventReviewTitle(eventReview.getEventReviewTitle());
            dto.setText(dto.getText());
            dto.setReviewRate(dto.getReviewRate());
            dtos.add(dto);
        }
        return dtos;
    }

    public EventReview getReviewEvent(Long id){
        EventReview eventReview = eventReviewRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 이벤트 리뷰가 없습니다."));
        return  eventReview;
    }


    public void updateReviewEvent(Long id, UpdateEventReviewDto dto){
        // 조회
        Optional<EventReview> eventReview = eventReviewRepository.findById(id);
        if (eventReview.isEmpty()){
            throw new RuntimeException("존재하지 않는 리뷰입니다.");
        }
        EventReview use = eventReview.get();

        use.update(dto);

        eventReviewRepository.save(use);
    }

    public void deleteEventReveiw( Long id){
        eventReviewRepository.deleteById(id);
    }
}
