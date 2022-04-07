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
    public void createOpTime (ResponseOpTimeDto dto) {
        //해당 placeId가 존재하는지 확인한 후,
        placeRepository.findById(dto.getPlaceId())
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));

        // 존재하는 경우 해당 optime을 dto내용에 맞추어 생성시키자.
        OperatingTime operatingTime = OperatingTime.of(dto);
        operatingTimeRepository.save(operatingTime);
    }

    // for Get All
    public List<OperatingTime> getOptimeAll(Long id){
        placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 존재하지 않습니다."));

        Optional<List<OperatingTime>> optimes = Optional.ofNullable(operatingTimeRepository.findAllByPlaceId(id));
        if(optimes.isEmpty()) {
            throw new IllegalArgumentException("해당 장소의 운영시간은 등록되지 않았습니다.");
        }
        return optimes.get();
    }
    /*
    //for Get Specific optime (about dateTime)
    public OperatingTime getOpTimeDatetime()
*/



    /*
    // for Get All
    public List<OperatingTime> getOptimeAll_inSpecificPlace(Long placeId ){
        placeRepository.findById(placeId)
                .orElseThrow(()-> new RuntimeException("해당 장소가 존재하지 않습니다."));

        List<OperatingTime> operatingTimes = operatingTimeRepository.findAllByPlaceId(placeId);

        return operatingTimes; // TODO: Controller에서 예외처리 해야 한다.
    } */





}
