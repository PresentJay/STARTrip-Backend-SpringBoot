package com.startrip.codebase.util.datatest;

import com.startrip.codebase.domain.datatest.DataTest;
import com.startrip.codebase.domain.datatest.DataTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AreaBased {
    private final DataTestRepository dataTestRepository;
    //private final DataTestDto dataTestDto;
    public String get() throws FileNotFoundException {

        try {
            // 데이터 삽입
            BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/com/startrip/codebase/store/지역기반 관광정보조회.json"));
            String str;
            List<String> save = new ArrayList<>();
            List<List<String>> res = new ArrayList<>();
            //List<UUID> uuidList = new ArrayList<>();
            while ((str = fileReader.readLine()) != null) {
                if (str.contains("addr1")) {
                    String s = str.trim().substring(9);
                    if (s.contains("\""))
                        s = s.split("\"")[1];
                    else
                        s = s.split(",")[0];
                    //System.out.println(s);
                    save.add(s);
                }
                if (str.contains("mapx")) {
                    String s = str.trim().substring(8);
                    if (s.contains("\""))
                        s = s.split("\"")[1];
                    else
                        s = s.split(",")[0];
                    //System.out.println(s);
                    save.add(s);
                }
                if (str.contains("mapy")) {
                    String s = str.trim().substring(8);
                    if (s.contains("\""))
                        s = s.split("\"")[1];
                    else
                        s = s.split(",")[0];
                    //System.out.println(s);
                    save.add(s);
                }
                if (str.contains("title")) {
                    String s = str.trim().substring(9).split("\"")[1];
                    //System.out.println(s);
                    UUID id = UUID.randomUUID();
                    save.add(s);
                    save.add(String.valueOf(id));
                    if (save.size() == 5) // 모든 정보가 다 있는 경우
                        // id, title, mapx, mapy, addr
                        dataTestRepository.save(new DataTest(UUID.fromString(save.get(4)), save.get(3), Double.parseDouble(save.get(1)), Double.parseDouble(save.get(2)), save.get(0)));
                    else if (save.size() == 4) // 주소 정보가 없는 경우
                        dataTestRepository.save(new DataTest(UUID.fromString(save.get(3)), save.get(2), Double.parseDouble(save.get(0)), Double.parseDouble(save.get(1)), ""));
                    else if (save.size() == 3) // 좌표 정보가 없는 경우
                        dataTestRepository.save(new DataTest(UUID.fromString(save.get(2)), save.get(1), 0.0, 0.0, save.get(0)));
                    else // 타이틀만 있는 경우
                        dataTestRepository.save(new DataTest(UUID.fromString(save.get(1)), save.get(0), 0.0, 0.0, ""));
                    //res.add(save);
                    //uuidList.add(id);
                    save = new ArrayList<>();
                }
            }

            // 중복 판별
            List<DataTest> dataTests = dataTestRepository.findAll();
            List<String> equal = new ArrayList<>();
            for (DataTest dataTest : dataTests) {
                for (DataTest dataTest1 : dataTests) {
                    if (!Objects.equals(dataTest.getPlaceName(), dataTest1.getPlaceName())) { // 자기 자신도 비교하므로 걸러주기
                        if (dataTest.getMapx() != 0.0 && dataTest1.getMapx() != 0.0) { // 좌표 정보가 있는 경우
                            if (Objects.equals(dataTest.getMapx(), dataTest1.getMapx()) && Objects.equals(dataTest.getMapy(), dataTest1.getMapy())) { // 좌표가 같은지 비교
                                //System.out.println(dataTest.getPlaceName() + " / " + dataTest1.getPlaceName());

                                if (!equal.contains(dataTest.getPlaceName()) && !equal.contains(dataTest1.getPlaceName())) {
                                    equal.add(dataTest.getPlaceName());
                                    equal.add(dataTest1.getPlaceName());
                                }
                            }
                        } else if (dataTest.getMapx() == 0.0 && dataTest1.getMapx() == 0.0) { // 좌표 정보가 없는 경우
                            if (dataTest.getPlaceName().contains(dataTest1.getPlaceName())) { // 타이틀이 비슷한 경우
                                if (!Objects.equals(dataTest.getAddress(), "") && !Objects.equals(dataTest1.getAddress(), "") && Objects.equals(dataTest.getAddress(), dataTest1.getAddress())) { // 주소가 같은 경우
                                    //System.out.println(dataTest.getPlaceName() + " / " + dataTest1.getPlaceName());

                                    if (!equal.contains(dataTest.getPlaceName()) && !equal.contains(dataTest1.getPlaceName())) {
                                        equal.add(dataTest.getPlaceName());
                                        equal.add(dataTest1.getPlaceName());
                                    }
                                }
                            }
                        } else if (dataTest.getMapx() != 0.0 && dataTest1.getMapx() != 0.0 && Objects.equals(dataTest.getAddress(), "") && Objects.equals(dataTest1.getAddress(), "")) { // 주소 정보가 없는 경우
                            if (Objects.equals(dataTest.getMapx(), dataTest1.getMapx()) && Objects.equals(dataTest.getMapy(), dataTest1.getMapy())) { // 좌표가 같은지 비교
                                //System.out.println(dataTest.getPlaceName() + " / " + dataTest1.getPlaceName());

                                if (!equal.contains(dataTest.getPlaceName()) && !equal.contains(dataTest1.getPlaceName())) {
                                    equal.add(dataTest.getPlaceName());
                                    equal.add(dataTest1.getPlaceName());
                                }
                            }
                        } else { // 타이틀만 있는 경우
                            if (dataTest.getPlaceName().contains(dataTest1.getPlaceName())) {
                                //System.out.println(dataTest.getPlaceName() + " / " + dataTest1.getPlaceName());

                                if (!equal.contains(dataTest.getPlaceName()) && !equal.contains(dataTest1.getPlaceName())) {
                                    equal.add(dataTest.getPlaceName());
                                    equal.add(dataTest1.getPlaceName());
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(equal);
            System.out.println(equal.size());

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok";
    }
}
