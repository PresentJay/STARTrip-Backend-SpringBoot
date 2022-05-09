package com.example.querydsldemo.curation;

import com.example.querydsldemo.curation.chains.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class CurationManager {
    private final JPAQueryFactory jpaQueryFactory;
    private List<CurationChain> chains;

    @Autowired
    public CurationManager(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
        setup();
    }

    private void setup() {
        chains = new ArrayList<>();
        chains.add(new DateCuration());
        chains.add(new DateTimeCuration());
        chains.add(new FeeCuration());
        chains.add(new LocationCuration());
        chains.add(new TagCuration());

        for (int i = 0; i < chains.size() - 1; i++){
            chains.get(i).setNextChain(chains.get(i+1));
        }
    }

    public BooleanBuilder start(HashMap<ChainType, Object> inputCurations) {
        BooleanBuilder whereClause = new BooleanBuilder();
        try {
            chains.stream().findFirst().get().curation(inputCurations, whereClause);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            return whereClause;
        }
    }
}
