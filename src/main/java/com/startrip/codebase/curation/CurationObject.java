package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.chains.ChainType;

import java.util.HashMap;
import java.util.Map;

public class CurationObject {
    public BooleanBuilder booleanBuilder = new BooleanBuilder();
    public Map<ChainType, Object> userInput = new HashMap<ChainType, Object>();

    @Override
    public String toString() {
        return "CurationObject{" +
                "booleanBuilder=" + booleanBuilder.getValue() +
                ", userInput Keys =" + userInput.keySet() +
                ", userInput Values =" + userInput.values() +
                '}';
    }
}
