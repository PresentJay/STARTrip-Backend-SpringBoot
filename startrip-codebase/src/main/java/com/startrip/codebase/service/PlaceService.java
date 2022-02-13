package com.startrip.codebase.service;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public List<Place> findPlace_nameContains(String place_name){
        return this.placeRepository.findByPlace_nameContains(place_name);
    }

    public List<Place> findPlace_nameStartwith(String place_name){
        return this.placeRepository.findByPlace_nameStartwith(place_name);

    }

    public List<Place> findPlace_nameEndswith(String item){
        return this.placeRepository.findByPlace_nameEndsWith(item);
    }


    public Map<String, List<Place>> findGTLT(Integer average_view_time){
        List<Place> gt = this.placeRepository.findByAverage_view_timeGreaterThan(average_view_time);
        List<Place> gte = this.placeRepository.findByAverage_view_timeGreaterThanEqual(average_view_time);
        List<Place> lt = this.placeRepository.findByAverage_view_timeLessThan(average_view_time);
        List<Place> lte = this.placeRepository.findByAverage_view_timeLessThanEqual(average_view_time);

        Map<String, List<Place>> ret = new HashMap<>();
        ret.put("gt", gt); //
        ret.put("gte", gte);
        ret.put("lt", lt);
        ret.put("lte", lte);

        return ret;
    }

}

