package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.chains.FeeCuration;

public class CurationLine {
    private final Curation currentCuration;

    CurationLine(Curation currentCuration){
        this.currentCuration = currentCuration;
    }

    CurationLine addCuration(Curation newCuration){
        return new CurationLine (
                (curationReturnVal) -> newCuration.curationProcess(
                        currentCuration.curationProcess(curationReturnVal)));
    }

    CurationReturnVal execute(CurationReturnVal curationReturnVal){
        return currentCuration.curationProcess(curationReturnVal);
    }
}