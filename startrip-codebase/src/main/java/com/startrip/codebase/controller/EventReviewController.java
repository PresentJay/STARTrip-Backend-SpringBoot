package com.startrip.codebase.controller;

import com.startrip.codebase.domain.event_review.EventReview;
import com.startrip.codebase.domain.event_review.dto.CreateEventReviewDto;
import com.startrip.codebase.domain.event_review.dto.ResponseEventReviewDto;
import com.startrip.codebase.domain.event_review.dto.UpdateEventReviewDto;
import com.startrip.codebase.service.EventReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EventReviewController {

    private final EventReviewService eventReviewService;

    @Autowired
    public EventReviewController(EventReviewService eventReviewService) {
        this.eventReviewService = eventReviewService;
    }

    //전체 조회
    @GetMapping("/eventReview")
    public List<ResponseEventReviewDto> getEventReviews() {
        List<ResponseEventReviewDto> eventReviews = eventReviewService.allEventReview();
        return eventReviews;
        //ToDo: try-catch로 예외처리하기
    }

    //생성
    @PostMapping("/eventReview")
    public ResponseEntity
    createEvent(@RequestBody CreateEventReviewDto dto) {
        UUID id = eventReviewService.createEventReview(dto);
        return new ResponseEntity(id, HttpStatus.OK);
    }

    //상세 조회
    @GetMapping("/eventReview/{id}")
    public ResponseEntity getReviewEvent(@PathVariable("id") UUID id) {
        EventReview eventReviews;
        try{
            eventReviews = eventReviewService.getReviewEvent(id);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(eventReviews, HttpStatus.OK);
    }

    //수정
    @PutMapping("/eventReview/{id}")
    public ResponseEntity updateReviewEvent(@PathVariable("id") UUID id, @RequestBody UpdateEventReviewDto dto) {
        try{
            eventReviewService.updateReviewEvent(id, dto);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }

    //삭제
    @DeleteMapping("/eventReview/{id}")
    public String deleteEventReveiw(@PathVariable("id") UUID id){
        eventReviewService.deleteEventReveiw(id);
        return "삭제";
    }
}