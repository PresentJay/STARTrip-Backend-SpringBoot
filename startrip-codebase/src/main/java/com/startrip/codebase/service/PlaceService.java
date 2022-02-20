package com.startrip.codebase.service;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<Place> list(int page) {
        //Page<Place>를 반환함, 인자로 넘어온 페이지를 보게 됨.
        //한 페이지당 데이터를 10개씩 가져옴, 정렬방식은 (내림차순, 플레이스_네임을 기준으로)
        return this.placeRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "place_name")));

    }



}

