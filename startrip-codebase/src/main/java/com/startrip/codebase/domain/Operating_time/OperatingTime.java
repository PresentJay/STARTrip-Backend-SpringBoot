package com.startrip.codebase.domain.Operating_time;

import com.startrip.codebase.dto.operatingTime.ResponseOpTimeDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperatingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 총 운영 시간, 브레이크 운영 시간, 주말 운영 등등 여러 엔티티가 생겨날 테니 자체 아이디가 있어야 함

    @NotNull
    @Setter
    @Column(name = "place_id")
    private Long placeId; // 얘는 이런 플레이스 꺼예요

    @Setter
    @Column(name = "start_date")
    private Date startDate;
    @Setter
    @Column(name = "end_date")
    private Date  endDate;

    @Setter
    @Column(name = "start_time")
    private Timestamp startTime;
    @Setter
    @Column(name = "end_time")
    private Timestamp  endTime;

    @Setter
    @Column(name = "is_breaktime")
    private Boolean isBreakTime;

    @Setter
    @Column(name = "cycle")
    private Integer cycle; // 1년 = 31,356,000 ... Integer = 2,147,483,648 ~ 2,147,483,647

    public void setId(Long placeId) {
        this.placeId = placeId;
    }


    public static OperatingTime createOperatingTime(Long placeId, ResponseOpTimeDto dto){
        OperatingTime operatingTime = OperatingTime.builder()
                .placeId(placeId)

                .build();

        return operatingTime;
    }
    @Builder
    public OperatingTime (Long placeId, Date startDate, Date endDate, Timestamp startTime, Timestamp endTime, Boolean isBreakTime, Integer cycle){

        this.placeId = placeId;

        this.startDate = startDate;
        this.endDate = endDate;

        this.startTime = startTime;
        this.endTime = endTime;

        this.isBreakTime = isBreakTime;
        this.cycle = cycle;
    }
}