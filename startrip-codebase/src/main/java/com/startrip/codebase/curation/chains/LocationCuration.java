package com.example.querydsldemo.curation.chains;

import com.example.querydsldemo.curation.CurationChain;
import com.example.querydsldemo.entity.QPlace;
import com.querydsl.core.BooleanBuilder;

import java.util.HashMap;

public class LocationCuration implements CurationChain {
    private CurationChain nextChain;
    double[] location = new double [2]; // 0: latitude, 1: longitude

    @Override
    public void setNextChain(CurationChain chain) {
        this.nextChain = chain;
    }

    @Override
    public void curation(HashMap<ChainType, Object> inputChains, BooleanBuilder whereClause) {
        if (inputChains.containsKey(ChainType.LOCATION)){
            location = (double [] )inputChains.get(ChainType.LOCATION);

            whereClause.and(QPlace.place.latitude.between(location[0]-0.1,location[0]+0.9)) // TODO: 범위값 조정
                        .and(QPlace.place.longitude.between(location[1]-2, location[1]+2)
            );
        }
        nextChain.curation(inputChains, whereClause);
    }
}
