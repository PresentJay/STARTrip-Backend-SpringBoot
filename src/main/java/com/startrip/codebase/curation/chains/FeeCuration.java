package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import com.startrip.codebase.curation.CurationReturnVal;
import com.startrip.codebase.domain.place.QPlace;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class FeeCuration implements Curation{
    CurationReturnVal curationReturnVal;

    @Override
    public CurationReturnVal curationProcess(CurationReturnVal curationReturnVal) {

        BooleanBuilder whereClause = curationReturnVal.getWhereClause();
        HashMap<ChainType, Object> input = curationReturnVal.getInput();
        Integer [] fee;
        fee = (Integer[])input.get(ChainType.FEE);
                /*whereClause.and(
                    QPlace.place.fee.between(fee[0], fee[1]) // todo: Place -> Event -> Event-Pricing -> price_range(double)조회
            );*/
        this.curationReturnVal = new CurationReturnVal(input, whereClause);
        log.info("FeeCuration 작동, 현 조건절: "+whereClause.toString());

        return curationReturnVal;
    }
}
