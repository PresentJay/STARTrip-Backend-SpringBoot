package com.startrip.codebase.service;

import com.startrip.codebase.domain.Operating_time.OperatingTime;
import com.startrip.codebase.domain.Operating_time.OperatingTimeRepository;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.operatingTime.RequestOptimeDto;
import com.startrip.codebase.dto.operatingTime.ResponseOptimeDto;
import com.startrip.codebase.dto.operatingTime.UpdateOptimePeriodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OperatingTimeService {

    private final OperatingTimeRepository operatingTimeRepository;
    private final PlaceRepository placeRepository;

    @Autowired
    public OperatingTimeService(OperatingTimeRepository operatingTimeRepository, PlaceRepository placeRepository) {
        this.operatingTimeRepository = operatingTimeRepository;
        this.placeRepository = placeRepository;
    }

    public void createOpTime (RequestOptimeDto dto) {
        FindPlace(dto.getPlaceId());

        OperatingTime operatingTime = OperatingTime.of(dto);
        operatingTimeRepository.save(operatingTime);
    }

    public List<OperatingTime> getOptimeAll(Long placeId){
        FindPlace(placeId);

        Optional<List<OperatingTime>> optimes = Optional.ofNullable(operatingTimeRepository.findAllByPlaceId(placeId));
        if(optimes.isEmpty()) {
            throw new RuntimeException( "해당 place에는 존재하는 운영정보가 없습니다");
        }
        return optimes.get();
    }

    //for Get optime In Specific datetime
    public Optional<OperatingTime> getOpTimeDatetime(Long placeId, LocalTime requestTime) {
        FindPlace(placeId);

        Optional<List<OperatingTime>> optimes = Optional.ofNullable(operatingTimeRepository.findAllByPlaceId(placeId));
        if(optimes.isEmpty()) {
            throw new RuntimeException("해당 place에는 존재하는 운영정보가 없습니다");
        }

        OperatingTime tmp = null;
        Boolean isInTime = false;
        for ( OperatingTime optime : optimes.get()) {
            isInTime = requestTime.isAfter(optime.getStartTime()) &&
                    requestTime.isBefore(optime.getEndTime());
            if (isInTime) {
                tmp = optime;
                break;
            }
        }

        if(!isInTime){
            throw new RuntimeException("해당 시간에 존재하는 운영정보가 없습니다.");
        }

        Optional<OperatingTime> optimeInTime = Optional.ofNullable(tmp);

        return optimeInTime;
    }

    public void updateOptime(UUID optimeId, UpdateOptimePeriodDto dto){
        OperatingTime optime = operatingTimeRepository.findById(optimeId)
                .orElseThrow( () -> new RuntimeException("해당 운영정보는 존재하지 않습니다"));

        optime.updateTime(dto);
        operatingTimeRepository.save(optime);
    }

    public void deleteOptime(UUID optimeId){
        operatingTimeRepository.findById(optimeId)
                .orElseThrow( () -> new RuntimeException("해당 운영정보는 존재하지 않습니다"));

        operatingTimeRepository.deleteById(optimeId);
    }

    public void FindPlace(Long placeId) { //TODO: placeId는 UUID로 변경될 여지가 있다
        placeRepository.findById(placeId)
                .orElseThrow( () -> new RuntimeException("해당 place는 존재하지 않습니다"));
    }

}
