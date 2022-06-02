package com.startrip.codebase.curation;

import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.chains.ChainType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CurationObjectSource implements Serializable {
    private BooleanBuilder booleanBuilder = new BooleanBuilder();
    private Map<ChainType, Object> userInput = new HashMap<ChainType, Object>();

    public BooleanBuilder getBooleanBuilder() {
        return this.booleanBuilder;
    }

    public void addInput(ChainType type, Object data) {
        this.userInput.put(type, data);
    }

    public Object getData(ChainType type) {
        return userInput.get(type);
    }

    @Override
    public String toString() {
        return "CurationObject{" +
                "booleanBuilder=" + booleanBuilder.getValue() +
                ", userInput Keys =" + userInput.keySet() +
                ", userInput Values =" + userInput.values() +
                '}';
    }
}
