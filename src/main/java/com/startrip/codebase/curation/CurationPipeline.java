package com.startrip.codebase.curation;

public class CurationPipeline<I, O> {
    private final CurationChain<I, O> currentChain;

    public CurationPipeline(CurationChain<I, O> currentChain) {
        this.currentChain = currentChain;
    }

    <K> CurationPipeline<I, K> addChain(CurationChain<O, K> newChain) {
        return new CurationPipeline<>(input -> newChain.process(currentChain.process(input)));
    }

    O execute(I input){
        return currentChain.process(input);
    }
}
