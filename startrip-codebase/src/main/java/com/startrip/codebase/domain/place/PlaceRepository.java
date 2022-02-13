package com.startrip.codebase.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

    // 장소 이름의 문자 포함
    List<Place> findByPlace_nameContains(String place_name);
    List<Place> findByPlace_nameStartwith(String place_name);
    List<Place> findByPlace_nameEndsWith(String place_name);

    // 장소 이용시간의 범위 검색
    List<Place> findByAverage_view_timeGreaterThan (Integer average_view_time);
    List<Place> findByAverage_view_timeGreaterThanEqual (Integer average_view_time);
    List<Place> findByAverage_view_timeLessThan (Integer average_view_time);
    List<Place> findByAverage_view_timeLessThanEqual (Integer average_view_time);

    // 정렬
    List<Place> findAllByOrderByPlace_nameDesc();

}
