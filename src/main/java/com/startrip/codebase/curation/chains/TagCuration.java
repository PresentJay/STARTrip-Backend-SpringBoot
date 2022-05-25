package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import com.startrip.codebase.curation.CurationReturnVal;
import com.startrip.codebase.domain.place.QPlace;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class TagCuration implements Curation{
    CurationReturnVal curationReturnVal;

    @Override
    public CurationReturnVal curationProcess(CurationReturnVal curationReturnVal) {

        BooleanBuilder whereClause = curationReturnVal.getWhereClause();
        HashMap<ChainType, Object> input = curationReturnVal.getInput();

        List<String> tags;
        tags = (List<String>) curationReturnVal.getInput().get(ChainType.TAG);
        for( String s:tags) {
            whereClause.and(QPlace.place.placeName.contains(s)
                    .or(QPlace.place.address.contains(s)) // 나중에는 분류된 카테고리에서도 검색되도록.
            );
        }
        this.curationReturnVal= new CurationReturnVal(input, whereClause);
        log.info("TagCuration작동, 현 조건절: "+whereClause.toString());

        return curationReturnVal;
    }
}
