package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.ChainType;
import com.startrip.codebase.curation.chains.TagFilter;
import com.startrip.codebase.curation.filterService.LocationFilterService;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.domain.place.QPlace;
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
class CurationManagerTest {
    Logger log = (Logger) LoggerFactory.getLogger(CurationManagerTest.class);

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @DisplayName("사용자가 공원,박물관 태그를 입력할시 테스트")
    @Test
    public void test1() {
        CurationObject curationObject = new CurationObject();
        List<String> userInputTags = new ArrayList<>();
        userInputTags.add("공원");
        userInputTags.add("박물관");

        curationObject.userInput.put(ChainType.TAG, userInputTags); // 사용자 입력

        var filters = new CurationPipeline<>(new TagFilter());
        CurationObject result = filters.execute(curationObject);

        System.out.println(curationObject.toString());
    }


    @DisplayName("국립 태그를 가진 데이터 조회")
    @Test
    void test2() {

        CurationObject curationObject = new CurationObject();
        List<String> userInputTags = new ArrayList<>();
        userInputTags.add("국립");
        curationObject.userInput.put(ChainType.TAG, userInputTags);

        var filters = new CurationPipeline<>(new TagFilter());
        CurationObject result = filters.execute(curationObject);
        BooleanBuilder whereClause = result.booleanBuilder;

        List<Place> placeList = jpaQueryFactory
                .selectFrom(QPlace.place)
                .where(whereClause)
                .fetch();
        placeList.forEach(System.out::println);
    }

    @DisplayName("국립 태그를 가진 특정 5km 이내 위치기반 검색")
    @Test
    void test3() {

        CurationObject curationObject = new CurationObject();

        List<String> userInputTags = new ArrayList<>();
        userInputTags.add("국립");
        curationObject.userInput.put(ChainType.TAG, userInputTags);

        Double [] userInputLocate = new Double [2];
        userInputLocate[0] =127.3512700000; //가나샤브샤브
        userInputLocate[1] =36.3890161659;

        curationObject.userInput.put(ChainType.TAG, userInputTags);
        curationObject.userInput.put(ChainType.LOCATION, userInputLocate);

        var filters = new CurationPipeline<>(new TagFilter())
                .addChain(new LocationFilterService());


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