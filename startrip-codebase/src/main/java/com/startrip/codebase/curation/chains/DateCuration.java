package com.example.querydsldemo.curation.chains;

import com.example.querydsldemo.curation.CurationChain;
import com.querydsl.core.BooleanBuilder;

import java.time.LocalDate;
import java.util.HashMap;

public class DateCuration implements CurationChain {
    private CurationChain nextChain;
    private LocalDate[] date = new LocalDate [2]; // 0:start, 1: end

    @Override
    public void setNextChain(CurationChain chain) {
        this.nextChain = chain;
    }

    @Override
    public void curation(HashMap<ChainType, Object> inputChains, BooleanBuilder whereClause) {
        if (inputChains.containsKey(ChainType.DATETIME)){
            date = (LocalDate []) inputChains.get(ChainType.DATETIME);
            /* whereClause.and(
                    QPlace.place. //TODO: Place → Event → start_date(DATE), end_date(DATE) 조회해야함
            ); */
        }
        nextChain.curation(inputChains, whereClause);
    }
}
