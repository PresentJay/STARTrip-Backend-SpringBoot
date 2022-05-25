package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.*;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.domain.place.QPlace;
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
public class CurationLineTest {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @DisplayName("경기도 리조트 찾기")
    @Test
    void test1() {
        HashMap<ChainType, Object> inputCurations = new HashMap<>();

        List<String> tags = new ArrayList<>();
        tags.add("경기도");
        tags.add("리조트");
        //Integer [] feeRange = {3000, 10000};
        //Double [] location = {126.0, 37.0};
        inputCurations.put(ChainType.TAG, tags);
        //inputCurations.put(ChainType.FEE, feeRange);
        //inputCurations.put(ChainType.LOCATION, location);
        BooleanBuilder whereClause = new BooleanBuilder();
        CurationReturnVal curationReturnVal = new CurationReturnVal(inputCurations, whereClause);

        var filters
                = new CurationLine(new TagCuration());
                //.addCuration(new LocationCuration())
                //.addCuration(new DateCuration());


        curationReturnVal =  filters.execute(curationReturnVal);
        whereClause = curationReturnVal.getWhereClause();

        List<Place> placeList = jpaQueryFactory
                .selectFrom(QPlace.place)
                .where(whereClause)
                .fetch();

        placeList.forEach(System.out::println);
    }

}
