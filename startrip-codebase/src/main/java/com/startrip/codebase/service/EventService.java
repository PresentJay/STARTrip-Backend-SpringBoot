package com.startrip.codebase.service;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.event.dto.CreateEventDto;
import com.startrip.codebase.domain.event.dto.ResponseEventDto;
import com.startrip.codebase.domain.event.dto.UpdateEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void createEvent(CreateEventDto dto) {
        Event event = Event.builder()
                .eventTitle(dto.getEventTitle())
                .description(dto.getDescription())
                .contact(dto.getContact())
                .build();
        eventRepository.save(event);
        log.info(event.toString());
    }

    public List<ResponseEventDto> allEvent() {
        List<Event> events = eventRepository.findAll();
        // Event -> ReponseEventDto
        List<ResponseEventDto> dtos = new ArrayList<>();

        for (Event event : events) {
            ResponseEventDto dto = new ResponseEventDto();
            dto.setEventTitle(event.getEventTitle());
            dto.setStartDate(event.getStartDate());
            dto.setEndDate(event.getEndDate());

            dtos.add(dto);
        }
        return dtos;
    }

    public Event getEvent(Long id){
        return eventRepository.findById(id).get();
    }


    public void updateEvent(Long id, UpdateEventDto dto){
        // 조회
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()){
            throw new RuntimeException("존재하지 않는 이벤트입니다.");
        }
        Event use = event.get();
        
        use.update(dto);
        
        eventRepository.save(use);
    }

    public void deleteEvent( Long id){
        eventRepository.deleteById(id);
    }
}
