package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.chains.ChainType;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class CurationReturnVal {
    private final HashMap<ChainType,Object> input;
    private final BooleanBuilder whereClause;

    public CurationReturnVal(HashMap<ChainType, Object> input, BooleanBuilder whereClause){
        this.input = input;
        this.whereClause = whereClause;
    }
}
