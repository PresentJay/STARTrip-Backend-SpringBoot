package com.startrip.codebase.util.datatest;

import com.startrip.codebase.domain.datatest.DataTest;
import com.startrip.codebase.domain.datatest.DataTestRepository;
import com.startrip.codebase.dto.datatest.DataTestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AreaBased {
    private final DataTestRepository dataTestRepository;
    public String get() throws FileNotFoundException {

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/com/startrip/codebase/store/지역기반 관광정보조회.json"));

            DataTestDto dto = new DataTestDto();
            String str;
            while ((str = fileReader.readLine()) != null) {
                if (str.contains("title")) {
                    String s = str.trim().substring(9).split("\"")[1];
                    System.out.println(s);
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
