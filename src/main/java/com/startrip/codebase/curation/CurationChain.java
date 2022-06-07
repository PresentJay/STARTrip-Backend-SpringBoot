package com.startrip.codebase.curation;



import com.querydsl.core.BooleanBuilder;
import com.startrip.codebase.curation.chains.ChainType;

import java.util.HashMap;

public interface CurationChain<I, O> {
    O process(I input);
}
