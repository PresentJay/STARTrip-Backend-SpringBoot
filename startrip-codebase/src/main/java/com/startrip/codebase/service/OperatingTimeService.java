package com.startrip.codebase.service;

import com.startrip.codebase.domain.Operating_time.OperatingTime;
import com.startrip.codebase.domain.Operating_time.OperatingTimeRepository;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.operatingTime.RequestOptimeDto;
import com.startrip.codebase.dto.operatingTime.ResponseOptimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

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

    // for Create
    public void createOpTime (RequestOptimeDto dto) {

        //해당 placeId가 존재하는지 확인한 후,
        FindPlace(dto.getPlaceId());

        // 존재하는 경우 해당 optime을 dto내용에 맞추어 생성시키자.
        OperatingTime operatingTime = OperatingTime.of(dto);
        operatingTimeRepository.save(operatingTime);
    }

    // for Get All
    public List<OperatingTime> getOptimeAll(Long placeId){

        FindPlace(placeId);

        Optional<List<OperatingTime>> optimes = Optional.ofNullable(operatingTimeRepository.findAllByPlaceId(placeId));
        if(optimes.isEmpty()) {
            throw new IllegalArgumentException("해당 장소의 운영시간은 등록되지 않았습니다.");
        }
        return optimes.get();
    }


    //for Get Specific optime (about dateTime)
    public Optional<OperatingTime> getOpTimeDatetime(Long placeId, LocalTime requestTime) {
        FindPlace(placeId);

        //해당 place에 존재하는 opTime들을 가져오기
        Optional<List<OperatingTime>> optimes = Optional.ofNullable(operatingTimeRepository.findAllByPlaceId(placeId));

        Boolean isInTime = false;
        OperatingTime optimeInTime=null;

        // 각 opTime에 대하여 탐색
        for ( OperatingTime optime : optimes.get()) {
            isInTime = requestTime.isAfter(optime.getStartTime()) && requestTime.isBefore(optime.getEndTime());
            if (isInTime) {
                optimeInTime = optime;
                break;
            }
        }

        return Optional.ofNullable(optimeInTime);
    }

    public void updateOptime(UUID optimeId, RequestOptimeDto dto){
        OperatingTime optime = operatingTimeRepository.findById(optimeId)
                .orElseThrow( () -> new IllegalArgumentException("해당 운영시간은 존재하지 않습니다."));

        optime.updateTime(dto);
        operatingTimeRepository.save(optime);
    }

    public void deleteOptime(UUID optimeId){
        operatingTimeRepository.findById(optimeId)
                .orElseThrow( () -> new IllegalArgumentException("해당 운영시간은 존재하지 않습니다."));

        operatingTimeRepository.deleteById(optimeId);
    }


    public void FindPlace(Long placeId) {
        placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));
    }

}
