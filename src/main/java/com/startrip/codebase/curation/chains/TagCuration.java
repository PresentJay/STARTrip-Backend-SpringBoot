package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.Curation;
import com.startrip.codebase.domain.place.QPlace;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class TagCuration
        implements Curation< HashMap<ChainType, List<String>>, BooleanBuilder>{

    @Override
    public BooleanBuilder curationProcess(HashMap<ChainType, List<String>> input) {
        List<String> tags;
        BooleanBuilder whereClause = new BooleanBuilder();
        tags = input.get(ChainType.TAG);
        for( String s:tags) {
            whereClause.and(QPlace.place.placeName.contains(s)
                    .or(QPlace.place.address.contains(s)) // 나중에는 분류된 카테고리에서도 검색되도록.
            );
        }

        log.info("TagCuration작동, 현 조건절: "+whereClause.toString());

        return whereClause;
    }
}
