package com.startrip.codebase.service.trip;

import com.startrip.codebase.domain.event_trip.EventTrip;
import com.startrip.codebase.domain.event_trip.EventTripRepository;
import com.startrip.codebase.dto.event_trip.CreateEventTripDto;
import com.startrip.codebase.dto.event_trip.ResponseEventTripDto;
import com.startrip.codebase.dto.event_trip.UpdateEventTripDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EventTripService {
    private final EventTripRepository eventTripRepository;

    @Autowired
    public EventTripService(EventTripRepository eventTripRepository) {
        this.eventTripRepository = eventTripRepository;
    }

    // Create
    public void createEventTrip(CreateEventTripDto dto) {
        EventTrip eventTrip = EventTrip.builder()
                .tripId(dto.getTripId())
                .userId(dto.getUserId())
                .userPartner(dto.getUserPartner())
                .eventId(dto.getEventId())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .state(dto.getState())
                .transportation(dto.getTransportation())
                .title(dto.getTitle())
                .build();

        eventTripRepository.save(eventTrip);
        log.info(eventTrip.toString());
    }

    // All
    public List<ResponseEventTripDto> allEventTrip() {
        List<EventTrip> eventTrips = eventTripRepository.findAll();
        List<ResponseEventTripDto> dtos = new ArrayList<>();

        for (EventTrip eventTrip : eventTrips) {
            ResponseEventTripDto dto = new ResponseEventTripDto();
            dto.setTripId(eventTrip.getTripId());
            dto.setEventId(eventTrip.getEventId());
            dto.setTitle(eventTrip.getTitle());

            dtos.add(dto);
        }
        return dtos;
    }

    // Get
    public EventTrip getEventTrip(UUID id){
        return eventTripRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchElementException("없는 데이터입니다.");
        });
    }

    // Update
    public void updateEventTrip(UUID id, UpdateEventTripDto dto){
        EventTrip eventTrip = eventTripRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 Event Trip 입니다.");
        });
        eventTrip.update(dto);
        eventTripRepository.save(eventTrip);
    }

    // Delete
    public void deleteEventTrip(UUID id) {
        eventTripRepository.deleteById(id);
    }
}
