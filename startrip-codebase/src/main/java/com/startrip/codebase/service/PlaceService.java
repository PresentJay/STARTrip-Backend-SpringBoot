package com.startrip.codebase.service;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public List<Place> findPlace_nameContains(String place_name){
        return this.placeRepository.findByPlaceNameContains(place_name);
    }

    public List<Place> findPlace_nameStartwith(String place_name){
        return this.placeRepository.findByPlaceNameStartsWith(place_name);
    }

    public List<Place> findPlace_nameEndswith(String item){
        return this.placeRepository.findByPlaceNameEndsWith(item);
    }

    public Map<String, List<Place>> findGTLT(Double average_view_time){
        List<Place> gt = this.placeRepository.findByAverageViewTimeGreaterThan(average_view_time);
        List<Place> gte = this.placeRepository.findByAverageViewTimeGreaterThanEqual(average_view_time);
        List<Place> lt = this.placeRepository.findByAverageViewTimeLessThan(average_view_time);
        List<Place> lte = this.placeRepository.findByAverageViewTimeLessThanEqual(average_view_time);

        Map<String, List<Place>> ret = new HashMap<>();
        ret.put("gt", gt);
        ret.put("gte", gte);
        ret.put("lt", lt);
        ret.put("lte", lte);

        return ret;
    }

    //sort
    public List<Place> allSortByRepository() {
        Sort sort = Sort.by(Sort.Direction.DESC, "average_view_time");
        return this.placeRepository.findAllByOrderByPlaceNameDesc();
    }

    public List<Place> allSortBySort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return this.placeRepository.findAll(sort);
    }

    public List<Place> allSortListOrder(){
        List<Order> sortList = new LinkedList<>();
        //이름 오름차순
        sortList.add(Order.asc("place_name"));

        //소요시간 내림차순
        sortList.add(Order.desc("average_view_time"));

        Sort sort = Sort.by(sortList);

        return this.placeRepository.findAll(sort);
    }


}

