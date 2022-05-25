package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import com.startrip.codebase.curation.CurationReturnVal;
import com.startrip.codebase.domain.place.QPlace;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class LocationCuration implements Curation{
    CurationReturnVal curationReturnVal;
    @Override
    public CurationReturnVal curationProcess(CurationReturnVal curationReturnVal) {

        BooleanBuilder whereClause = curationReturnVal.getWhereClause();
        HashMap<ChainType, Object> input = curationReturnVal.getInput();

        Double[] location; // 0: latitude, 1: longitude
        location = (Double[]) input.get(ChainType.LOCATION);

        whereClause.and(QPlace.place.latitude.between(location[0]-0.1,location[0]+0.9)) // TODO: 범위값 조정
                .and(QPlace.place.longitude.between(location[1]-2, location[1]+2)
                );

        this.curationReturnVal = new CurationReturnVal(input, whereClause);
        log.info("LocationCuration작동, 현 조건절: " + whereClause.toString());

        return curationReturnVal;
    }
}
