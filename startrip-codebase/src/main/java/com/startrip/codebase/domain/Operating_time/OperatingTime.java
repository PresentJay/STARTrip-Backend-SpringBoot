package com.startrip.codebase.domain.Operating_time;

import com.startrip.codebase.dto.operatingTime.RequestOptimeDto;
import com.startrip.codebase.dto.operatingTime.UpdateOptimePeriodDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperatingTime {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id; // 총 운영 시간, 브레이크 운영 시간, 주말 운영 등등 여러 엔티티가 생겨날 테니 자체 아이디가 있어야 함

    @NotNull
    @Setter
    @Column(name = "place_id")
    private UUID placeId; // 얘는 이런 플레이스 꺼예요

    @Setter
    @Column(name = "start_date")
    private LocalDate startDate;
    @Setter
    @Column(name = "end_date")
    private LocalDate  endDate;

    @Setter
    @Column(name = "start_time")
    private LocalTime startTime;
    @Setter
    @Column(name = "end_time")
    private LocalTime  endTime;

    @Setter
    @Column(name = "is_breaktime")
    private Boolean isBreakTime;

    @Setter
    @Column(name = "cycle")
    private Integer cycle; // 1년 = 31,356,000 ... Integer = 2,147,483,648 ~ 2,147,483,647

    public void setId(UUID placeId) {
        this.placeId = placeId;
    }

    public static OperatingTime of ( RequestOptimeDto dto){

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ISO_LOCAL_TIME;

        OperatingTime operatingTime = OperatingTime.builder()
                .placeId(dto.getPlaceId())
                .startDate(LocalDate.parse(dto.getStartDate(), formatterDate))
                .endDate(LocalDate.parse(dto.getEndDate(), formatterDate))
                .startTime(LocalTime.parse(dto.getStartTime(), formatterTime))
                .endTime(LocalTime.parse(dto.getEndTime(), formatterTime))
                .isBreakTime(dto.getIsBreakTime())
                .cycle(dto.getCycle())
                .build();
        return operatingTime;
    }

    public void updateTime(UpdateOptimePeriodDto dto){

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ISO_LOCAL_TIME;

        this.startDate = LocalDate.parse(dto.getStartDate(), formatterDate);
        this.endDate = LocalDate.parse(dto.getEndDate(), formatterDate);
        this.startTime = LocalTime.parse(dto.getStartTime(), formatterTime);
        this.endTime = LocalTime.parse(dto.getEndTime(), formatterTime);
    }

    @Builder
    public OperatingTime (UUID placeId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Boolean isBreakTime, Integer cycle){

        this.placeId = placeId;

        this.startDate = startDate;
        this.endDate = endDate;

        this.startTime = startTime;
        this.endTime = endTime;

        this.isBreakTime = isBreakTime;
        this.cycle = cycle;
    }


}
