package com.startrip.codebase.service;

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

    @Autowired
    public EventReviewService(EventReviewRepository eventReviewRepository) {
        this.eventReviewRepository = eventReviewRepository;
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
        return  eventReviewRepository.findById(id).get();
    }
    //ToDo: 해당 부분은 id 가 없을시엔, NPE 에러가 발생할 경우가 있으니, Optional 에서 orElseThrow() 를 던져서 예외를 처리


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
