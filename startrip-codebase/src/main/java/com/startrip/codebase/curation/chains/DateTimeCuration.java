package com.example.querydsldemo.curation.chains;

import com.example.querydsldemo.curation.CurationChain;
import com.querydsl.core.BooleanBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;

public class DateTimeCuration implements CurationChain {
    private CurationChain nextChain;
    private LocalDateTime[] datetime = new LocalDateTime [2]; // 0:start, 1: end

    @Override
    public void setNextChain(CurationChain chain) {
        this.nextChain = chain;
    }

    @Override
    public void curation(HashMap<ChainType, Object> inputChains, BooleanBuilder whereClause) {
        if (inputChains.containsKey(ChainType.DATETIME)){
            datetime = (LocalDateTime []) inputChains.get(ChainType.DATETIME);
            /* whereClause.and(  //TODO: Place → Operation_time → start_time(DATETIME), end_time(DATETIME) 조회
                    QPlace.place.
            ); */
        }
        nextChain.curation(inputChains, whereClause);
    }
}
