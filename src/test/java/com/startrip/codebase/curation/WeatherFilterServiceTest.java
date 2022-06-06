package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.ChainType;
import com.startrip.codebase.curation.filterService.WeatherFilterService;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.domain.place.QPlace;
import com.startrip.codebase.util.weather.WeatherApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class WeatherFilterServiceTest {
    Logger log = LoggerFactory.getLogger(WeatherFilterServiceTest.class);

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @DisplayName("날씨 점수 검색")
    @Test
    void test1() {

        CurationObject curationObject = new CurationObject();

        String userInputWeather = "1";
        curationObject.userInput.put(ChainType.WEATHER, userInputWeather);

        var filters = new CurationPipeline<>(new WeatherFilterService(placeRepository));
        CurationObject result = filters.execute(curationObject);
        BooleanBuilder whereClause = result.booleanBuilder;

        QPlace qPlace = QPlace.place;
        List<Place> placeList = jpaQueryFactory
                .select(qPlace)
                .from(qPlace)
                .where(whereClause)
                .fetch();

        log.info(whereClause.toString());
        placeList.forEach(System.out::println);
    }
}