package com.startrip.codebase.domain.event_trip;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.event_trip.UpdateEventTripDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EventTrip {
    @Id
    @Column(name = "trip_id")
    private UUID tripId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "user_partner")
    private String userPartner;

    @Column(name = "event_id")
    private UUID eventId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private Integer state;

    private String transportation;

    private String title;

    public void update(UpdateEventTripDto dto) {
        this.userPartner = dto.getUserPartner();
        this.eventId = dto.getEventId();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.state = dto.getState();
        this.transportation = dto.getTransportation();
        this.title = dto.getTitle();
    }
}
