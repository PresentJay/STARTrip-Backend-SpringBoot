package com.startrip.codebase.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {

    // 장소 이름의 문자 포함
    List<Place> findByPlaceNameContains(String placeName);
    List<Place> findByPlaceNameStartsWith(String placeName);
    List<Place> findByPlaceNameEndsWith(String placeName);

    // 장소 이용시간의 범위 검색
    List<Place> findByAverageViewTimeGreaterThan (Double averageViewTime);
    List<Place> findByAverageViewTimeGreaterThanEqual (Double averageViewTime);
    List<Place> findByAverageViewTimeLessThan (Double averageViewTime);
    List<Place> findByAverageViewTimeLessThanEqual (Double averageViewTime);

    // 정렬
    List<Place> findAllByOrderByPlaceNameDesc();

}
