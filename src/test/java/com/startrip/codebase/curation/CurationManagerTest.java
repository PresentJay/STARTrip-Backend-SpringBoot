package com.startrip.codebase.curation;

import com.startrip.codebase.curation.chains.ChainType;
import com.startrip.codebase.curation.chains.TagCuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurationManagerTest {

    @DisplayName("사용자가 공원,박물관 태그를 입력할시 테스트")
    @Test
    public void test0() {
        CurationObject curationObject = new CurationObject();
        List<String> userInputTags = new ArrayList<>();
        userInputTags.add("공원");
        userInputTags.add("박물관");

        curationObject.userInput.put(ChainType.TAG, userInputTags); // 사용자 입력

        var filters = new CurationPipeline<>(new TagCuration());
        CurationObject result = filters.execute(curationObject);

        System.out.println(curationObject.toString());
    }
}