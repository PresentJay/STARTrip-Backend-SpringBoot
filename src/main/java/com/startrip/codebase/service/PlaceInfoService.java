package com.startrip.codebase.service;

import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.domain.place_info.PlaceInfo;
import com.startrip.codebase.domain.place_info.PlaceInfoRepository;
import com.startrip.codebase.dto.PlaceInfoDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlaceInfoService {

    private PlaceInfoRepository placeInfoRepository;
    private PlaceRepository placeRepository;

    public PlaceInfoService(PlaceInfoRepository placeInfoRepository, PlaceRepository placeRepository) {
        this.placeInfoRepository = placeInfoRepository;
        this.placeRepository = placeRepository;
    }

    public Long createPlaceInfo(PlaceInfoDto dto) {
        placeRepository.findById(dto.getPlaceId())
                .orElseThrow(() -> new RuntimeException("해당 장소가 없습니다."));
        PlaceInfo placeInfo = PlaceInfo.of(dto);
        placeInfoRepository.save(placeInfo);
        return placeInfo.getId();
    }

    public void updatePlaceInfo(Long id, PlaceInfoDto dto){
        placeInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 PlaceInfo가 없습니다."));
        PlaceInfo placeInfo = getPlaceInfo(id);
        placeInfo.update(dto);
        placeInfoRepository.save(placeInfo);
    }

    public PlaceInfo getPlaceInfo(Long id) {
        Optional<PlaceInfo> placeInfo = placeInfoRepository.findById(id);
        if (placeInfo.isEmpty()) {
            throw new RuntimeException("해당 PlaceInfo가 없습니다");
        }
        return placeInfo.get();
    }

    public void deletePlaceInfo(Long id) {
        placeInfoRepository.deleteById(id);
    }
}
