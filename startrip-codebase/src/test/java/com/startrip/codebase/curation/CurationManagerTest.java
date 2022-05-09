package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.ChainType;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.domain.place.QPlace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Transactional
//Todo: Startip에 맞추어 로직 변경 후, 재 태스트 해야함
class CurationManagerTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    private void setUp() {

    }
    @DisplayName("경기도 리조트 찾기")
    @Test
    void test1() {
        HashMap<ChainType, Object> inputCurations = new HashMap<>();

        List<String> tags = new ArrayList<>();
        tags.add("경기도");
        tags.add("리조트");
        //int [] feeRange = {3000, 10000};
        //double [] location = {126, 37};
        inputCurations.put(ChainType.TAG, tags);
        //inputCurations.put(ChainType.FEE, feeRange);
        //inputCurations.put(ChainType.LOCATION, location);

        CurationManager curationManager = new CurationManager(jpaQueryFactory);
        BooleanBuilder whereClause = curationManager.start(inputCurations);

        List<Place> placeList = jpaQueryFactory
                .selectFrom(QPlace.place)
                .where(whereClause)
                .fetch();
        placeList.forEach(System.out::println);
    }

    @DisplayName("경기도 리조트인데 요금이 3000~10000사이인 것 찾기")
    @Test
    void test2() {
        HashMap<ChainType, Object> inputCurations = new HashMap<>();

        List<String> tags = new ArrayList<>();
        tags.add("경기도");
        tags.add("리조트");
        int [] feeRange = {3000, 10000};
        //double [] location = {126, 37}; // 서울 기준
        inputCurations.put(ChainType.TAG, tags);
        inputCurations.put(ChainType.FEE, feeRange);
        //inputCurations.put(ChainType.LOCATION, location);

        CurationManager curationManager = new CurationManager(jpaQueryFactory);
        BooleanBuilder whereClause = curationManager.start(inputCurations);

        List<Place> placeList = jpaQueryFactory
                .selectFrom(QPlace.place)
                .where(whereClause)
                .fetch();
        placeList.forEach(System.out::println);
    }

    @DisplayName("경기도 리조트인데 요금이 3000~10000사이인데다가 지정 위치 주변으로 찾기")
    @Test
    void test3() {
        HashMap<ChainType, Object> inputCurations = new HashMap<>();

        List<String> tags = new ArrayList<>();
        tags.add("경기도");
        tags.add("리조트");
        int [] feeRange = {3000, 10000};
        double [] location = {127, 36}; // 서울
        inputCurations.put(ChainType.TAG, tags);
        inputCurations.put(ChainType.FEE, feeRange);
        inputCurations.put(ChainType.LOCATION, location);

        CurationManager curationManager = new CurationManager(jpaQueryFactory);
        BooleanBuilder whereClause = curationManager.start(inputCurations);

        List<Place> placeList = jpaQueryFactory
                .selectFrom(QPlace.place)
                .where(whereClause)
                .fetch();
        placeList.forEach(System.out::println);
    }
}