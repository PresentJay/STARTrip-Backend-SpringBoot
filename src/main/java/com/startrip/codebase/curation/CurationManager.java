package com.startrip.codebase.curation;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.chains.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CurationManager {
    private final JPAQueryFactory jpaQueryFactory;
    private final CurationPipeline pipeline;

    @Autowired
    public CurationManager(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;

        pipeline = new CurationPipeline<>(new TagCuration());
    }

    public CurationObjectSource start(CurationObjectSource userSource) {
        try {
            pipeline.execute(userSource);
            return userSource;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
