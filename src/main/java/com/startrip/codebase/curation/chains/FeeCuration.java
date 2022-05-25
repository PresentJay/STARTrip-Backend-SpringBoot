package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import com.startrip.codebase.domain.place.QPlace;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class FeeCuration
        implements Curation< HashMap<ChainType, Integer[]>, BooleanBuilder> {

    @Override
    public BooleanBuilder curationProcess(HashMap<ChainType, Integer []> input) {
        Integer [] fee;
        Double[] location; // 0: latitude, 1: longitude
        BooleanBuilder whereClause = new BooleanBuilder();
        fee = input.get(ChainType.FEE);
                /*whereClause.and(
                    QPlace.place.fee.between(fee[0], fee[1]) // todo: Place -> Event -> Event-Pricing -> price_range(double)조회
            );*/
        log.info("FeeCuration 작동, 현 조건절: "+whereClause.toString());
        return whereClause;
    }
}
