package com.startrip.codebase.curation.chains;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.CurationChain;
import com.startrip.codebase.curation.CurationObject;
import com.startrip.codebase.domain.place.QPlace;
import java.util.HashMap;
import java.util.List;

public class TagCuration implements CurationChain<CurationObject, CurationObject> {

    @Override
    public CurationObject process(CurationObject input) {
        Object object = input.userInput.get(ChainType.TAG);
        if (object instanceof List){
            List<String> userTags = (List<String>) object;
            for (String tag : userTags) {
                input.booleanBuilder.or(QPlace.place.placeName.contains(tag).or(QPlace.place.address.contains(tag)));
            }

        }
        return input;
    }
}
