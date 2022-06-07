package com.startrip.codebase.service;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;

import com.startrip.codebase.domain.place_info.PlaceInfo;
import com.startrip.codebase.domain.place_info.PlaceInfoRepository;
import com.startrip.codebase.dto.PlaceDto;
import com.startrip.codebase.dto.PlaceInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public List<Place> allPlace() {
        return placeRepository.findAll();
    }

    public List<Place> categoryPlace(UUID category_id) {
        List<Place> placeList = placeRepository.findByCategoryId(category_id);
        if (placeList.isEmpty()) {
            throw new RuntimeException("해당 카테고리의 장소가 없습니다");
        }
        return placeList;
    }

    public Place getPlace(UUID id) {
        Optional<Place> place = placeRepository.findById(id);
        if (place.isEmpty()) {
            throw new RuntimeException("해당 장소가 없습니다");
        }
        return place.get();
    }

    public UUID createPlace(PlaceDto dto) {
        Place place = Place.of(dto);
        placeRepository.save(place);
        return place.getId();
    }

    public void updatePlace(UUID id, PlaceDto dto){
        Place place = getPlace(id);
        place.update(dto);
        placeRepository.save(place);
    }

    public void deletePlace(UUID id) {
        placeRepository.deleteById(id);
    }

}

