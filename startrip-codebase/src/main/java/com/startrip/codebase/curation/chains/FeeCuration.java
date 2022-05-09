package com.example.querydsldemo.curation.chains;

import com.example.querydsldemo.curation.CurationChain;
import com.example.querydsldemo.entity.QPlace;
import com.querydsl.core.BooleanBuilder;

import java.util.HashMap;

public class FeeCuration implements CurationChain {
    private CurationChain nextChain;
    private int [] fee = new int [2]; // 0:start, 1: end

    @Override
    public void setNextChain(CurationChain chain) {
        this.nextChain = chain;
    }

    @Override
    public void curation(HashMap<ChainType, Object> inputChains, BooleanBuilder whereClause) {
        if (inputChains.containsKey(ChainType.FEE)){
             fee = (int []) inputChains.get(ChainType.FEE);
                whereClause.and(
                    QPlace.place.fee.between(fee[0], fee[1]) // todo: Place -> Event -> Event-Pricing -> price_range(double)조회
            );
        }
        nextChain.curation(inputChains, whereClause);
    }
}
