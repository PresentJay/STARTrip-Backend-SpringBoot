package com.startrip.codebase.curation;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class CurationManager {
    private final JPAQueryFactory jpaQueryFactory;
    private final CurationPipeline pipeline;

    @Autowired
    public CurationManager(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;

        pipeline = new CurationPipeline(new TagCuration());
    }

    public CurationObject start() {
        try {
            CurationObject curationObject = new CurationObject();
            pipeline.execute(curationObject);
            return curationObject;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
