package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

@Slf4j
public class DateTimeCuration
        implements Curation< HashMap<ChainType, LocalDateTime[]>, BooleanBuilder> {

    @Override
    public BooleanBuilder curationProcess(HashMap<ChainType, LocalDateTime[]> inputChains ) {
        LocalDateTime [] datetime;
        BooleanBuilder whereClause = new BooleanBuilder();
        if (inputChains.containsKey(ChainType.DATETIME)){
            datetime = inputChains.get(ChainType.DATETIME);
            /* whereClause.and(  //TODO: Place → Operation_time → start_time(DATETIME), end_time(DATETIME) 조회
                    QPlace.place.
            ); */
        }
        log.info("DateTimeCuration작동, 현 조건절: "+whereClause.toString());
       return whereClause;
    }
}
