package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
public class DateCuration
        implements Curation< HashMap<ChainType, LocalDate[]>, BooleanBuilder> {

    @Override
    public BooleanBuilder curationProcess(HashMap<ChainType, LocalDate[]> inputChains ) {
        LocalDate [] date;
        BooleanBuilder whereClause = new BooleanBuilder();
        if (inputChains.containsKey(ChainType.DATETIME)){
            date = inputChains.get(ChainType.DATETIME);
            /* whereClause.and(  //TODO: Place → Operation_time → start_time(DATETIME), end_time(DATETIME) 조회
                    QPlace.place.
            ); */
        }
        log.info("DateCuration작동, 현 조건절: "+whereClause.toString());
        return whereClause;
    }
}
