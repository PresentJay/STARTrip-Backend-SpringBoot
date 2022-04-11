package com.startrip.codebase.service;

import com.startrip.codebase.domain.place_info.PlaceInfo;
import com.startrip.codebase.domain.place_info.PlaceInfoRepository;
import com.startrip.codebase.dto.PlaceInfoDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceInfoService {

    private PlaceInfoRepository placeInfoRepository;

    public PlaceInfoService(PlaceInfoRepository placeInfoRepository) {
        this.placeInfoRepository = placeInfoRepository;
    }

    public void createPlaceInfo(PlaceInfoDto dto) {
        //placeRepository.findById(dto.getPlaceId())
        //        .orElseThrow(() -> new RuntimeException("해당 장소가 없습니다."));
        PlaceInfo placeInfo = PlaceInfo.of(dto);
        placeInfoRepository.save(placeInfo);
    }

    public void updatePlaceInfo(Long id, PlaceInfoDto dto){
        placeInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 PlaceInfo가 없습니다."));
        PlaceInfo placeInfo = getPlaceInfo(id);
        placeInfo.update(dto);
        placeInfoRepository.save(placeInfo);
    }

    public PlaceInfo getPlaceInfo(Long placeId) {
        Optional<PlaceInfo> placeInfo = placeInfoRepository.findById(placeId);
        if (placeInfo.isEmpty()) {
            throw new RuntimeException("해당 PlaceInfo가 없습니다");
        }
        return placeInfo.get();
    }

    public void deletePlaceInfo(Long id) {
        placeInfoRepository.deleteById(id);
    }
}
