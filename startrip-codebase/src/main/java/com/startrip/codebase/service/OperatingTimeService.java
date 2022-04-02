package com.startrip.codebase.service;

import com.startrip.codebase.domain.Operating_time.OperatingTime;
import com.startrip.codebase.domain.Operating_time.OperatingTimeRepository;
import com.startrip.codebase.domain.category.CategoryRepository;
import com.startrip.codebase.domain.notice.Notice;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.dto.operatingTime.ResponseOpTimeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void createOpTime (Long placeId , ResponseOpTimeDto dto) {
        //해당 placeId가 존재하는지 확인한 후,
        placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("해당 장소가 존재하지 않습니다."));

        // 존재하는 경우 해당 optime을 dto내용에 맞추어 생성시키자.
        OperatingTime operatingTime = OperatingTime.of(placeId, dto);

        operatingTimeRepository.save(operatingTime);
    }

    // for Get All
    public List<OperatingTime> getOpTimeAll(){
        return operatingTimeRepository.findAll(); // TODO: 이거는 NPE가 나타나지 않을까?
    }

    /*
    // for Get All
    private List<OperatingTime> (Long placeId ){

    }
    */




}
