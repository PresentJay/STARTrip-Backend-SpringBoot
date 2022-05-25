package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.chains.ChainType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface Curation {
    // Input : HashMap <ChainType, Object>
    // Out : BooleanBuilder
    // HashMap을 넣으면 OWhereClause 출력되도록 함.


    CurationReturnVal curationProcess(CurationReturnVal curationReturnVal);


}