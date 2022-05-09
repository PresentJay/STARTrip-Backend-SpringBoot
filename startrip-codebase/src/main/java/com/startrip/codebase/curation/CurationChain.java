package com.example.querydsldemo.curation;

import com.example.querydsldemo.curation.chains.ChainType;
import com.example.querydsldemo.entity.Place;
import com.querydsl.core.BooleanBuilder;

import java.util.HashMap;
import java.util.List;

public interface CurationChain {
    void setNextChain(CurationChain chain);
    void curation(HashMap<ChainType, Object> tags, BooleanBuilder whereClause);
}
