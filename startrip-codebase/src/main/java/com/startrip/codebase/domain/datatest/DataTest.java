package com.startrip.codebase.domain.datatest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DataTest {
    @Id
    @Column(name = "test_id")
    private UUID testId;

    @Column(name = "place_name")
    private String placeName; // json 파일의 title 부분을 넣기

    private double mapx; // GPS X좌표

    private double mapy; // GPS Y좌표

    private String address;
}
