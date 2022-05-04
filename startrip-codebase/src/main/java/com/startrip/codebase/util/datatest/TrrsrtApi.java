package com.startrip.codebase.util.datatest;

import com.startrip.codebase.domain.datatest.DataTest;
import com.startrip.codebase.domain.datatest.DataTestRepository;
import com.startrip.codebase.dto.datatest.DataTestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class TrrsrtApi {
    private final DataTestRepository dataTestRepository;
    public String get() throws FileNotFoundException {

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/com/startrip/codebase/store/전국관광지정보표준데이터.xml"));

            DataTestDto dto = new DataTestDto();
            String str;
            while ((str = fileReader.readLine()) != null) {
                if (str.contains("trrsrtNm")) {
                    String s = str.trim().substring(10).split("<")[0];
                    //System.out.println(s);
                    dataTestRepository.save(new DataTest(UUID.randomUUID(), s));
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok";
    }
}
