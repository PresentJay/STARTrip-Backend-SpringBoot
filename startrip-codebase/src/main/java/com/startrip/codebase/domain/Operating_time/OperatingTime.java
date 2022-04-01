package com.startrip.codebase.domain.Operating_time;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperatingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Setter
    @Column(name = "place_id")
    private Long placeId; // 얘는 이런 플레이스 꺼예요


    @Setter
    @Column(name = "stattime_WeekDay")
    private OffsetDateTime starttimeWeekday; // 평일 start Time


    @Setter
    @Column(name = "endtime_Weekday")
    private OffsetDateTime endtimeWeekday; // 평일 end Time


    @Setter
    @Column(name = "starttime_weekend")
    private OffsetDateTime starttimeWeekend; // 주말 start Time
    @Setter
    @Column(name = "endtime_weekend")
    private OffsetDateTime endtimeWeekend; // 주말 end Time


    @Setter
    private Integer closedayofweek; // 0-6 : 일-토
    @Setter
    private Integer closeDayscheduled; // 날짜(일)
    @Setter
    private Date closedaytemporary; // 년중 날짜 받기

    public void setId(Long id) {
        this.id = id;
    }

    @Builder
    public OperatingTime (Long placeId, OffsetDateTime starttimeWeekday, OffsetDateTime endtimeWeekday,
                          OffsetDateTime starttimeWeekend, OffsetDateTime endtimeWeekend,
                          Integer closeayofweek, Integer closeDayscheduled, Date closedaytemporary){

        this.placeId = placeId;
        this.starttimeWeekday = starttimeWeekday;
        this.endtimeWeekday = endtimeWeekday;

        this.starttimeWeekend = starttimeWeekend;
        this.endtimeWeekend = endtimeWeekend;

        this.closedayofweek = closeayofweek;
        this.closeDayscheduled = closeDayscheduled;
        this.closedaytemporary = closedaytemporary;
    }
}
