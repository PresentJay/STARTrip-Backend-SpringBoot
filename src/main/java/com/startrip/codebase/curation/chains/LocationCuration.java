package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import com.startrip.codebase.domain.place.QPlace;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class LocationCuration
        implements Curation< HashMap<ChainType, Double []>, BooleanBuilder> {

    @Override
    public BooleanBuilder curationProcess(HashMap<ChainType, Double []> input) {
        Double[] location; // 0: latitude, 1: longitude
        BooleanBuilder whereClause = new BooleanBuilder();
        location = input.get(ChainType.LOCATION);

        whereClause.and(QPlace.place.latitude.between(location[0]-0.1,location[0]+0.9)) // TODO: 범위값 조정
                .and(QPlace.place.longitude.between(location[1]-2, location[1]+2)
                );

        log.info("LocationCuration작동, 현 조건절: "+whereClause.toString());
        return null;
    }
}
