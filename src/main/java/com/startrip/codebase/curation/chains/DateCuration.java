package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import com.startrip.codebase.curation.CurationReturnVal;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
public class DateCuration implements Curation{
    CurationReturnVal curationReturnVal;

    @Override
    public CurationReturnVal curationProcess(CurationReturnVal curationReturnVal) {

        BooleanBuilder whereClause = curationReturnVal.getWhereClause();
        HashMap<ChainType, Object> input = curationReturnVal.getInput();

        LocalDateTime[] date;
        date = (LocalDateTime[]) curationReturnVal.getInput().get(ChainType.DATE);

        /* whereClause.and(  //TODO: Place → Operation_time → start_time(DATETIME), end_time(DATETIME) 조회
                QPlace.place.
        ); */
        this.curationReturnVal= new CurationReturnVal(input, whereClause);
        log.info("DateTimeCuration작동, 현 조건절: "+whereClause.toString());

        return curationReturnVal;
    }
}
