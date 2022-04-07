package com.startrip.codebase.service;

import com.startrip.codebase.domain.Operating_time.OperatingTime;
import com.startrip.codebase.domain.Operating_time.OperatingTimeRepository;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.operatingTime.ResponseOptimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void createOpTime (ResponseOptimeDto dto) {
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
/*
    //for Get Specific optime (about dateTime)
    public OperatingTime getOpTimeDatetime(Long placeId, @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
        FindPlace(placeId);

        //해당 date가, 어떤 optime이 가진 시간 내에 가지고 있는 것인지 구현해야 함

        Optional<List<OperatingTime>> optimes = Optional.ofNullable(operatingTimeRepository.findAllByPlaceId(placeId));
        for ( OperatingTime optime : optimes.get()){

            // 아니, 어떻게 비교해?
            if(optime.getStartTime() < date < optime.getEndTime())

        }

    } */

    public void updateOptime(UUID optimeId, ResponseOptimeDto dto){
        OperatingTime optime = operatingTimeRepository.findById(optimeId)
                .orElseThrow( () -> new IllegalArgumentException("해당 운영시간은 존재하지 않습니다."));

        optime.updateTime(dto);
        operatingTimeRepository.save(optime);
    }




    public void FindPlace(Long placeId) {
        placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));
    }

    /*
    // for Get All
    public List<OperatingTime> getOptimeAll_inSpecificPlace(Long placeId ){
        placeRepository.findById(placeId)
                .orElseThrow(()-> new RuntimeException("해당 장소가 존재하지 않습니다."));

        List<OperatingTime> operatingTimes = operatingTimeRepository.findAllByPlaceId(placeId);

        return operatingTimes; // TODO: Controller에서 예외처리 해야 한다.
    } */





}
