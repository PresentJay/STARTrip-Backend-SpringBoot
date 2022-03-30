package com.startrip.codebase.domain.place_trip;

import com.startrip.codebase.domain.state.State;
import com.startrip.codebase.dto.place_trip.CreatePlaceTripDto;
import com.startrip.codebase.dto.place_trip.UpdatePlaceTripDto;
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
public class PlaceTrip {
    @Id
    @Column(name = "trip_id")
    private UUID tripId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_partner")
    private String userPartner;

    @Column(name = "place_id")
    private UUID placeId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @OneToOne(cascade = CascadeType.ALL) // fetch = FetchType.LAZY를 하면 오류 발생
    @JoinColumn(name="state_id")
    private State state;

    private String transportation;

    private String title;

    public void update(UpdatePlaceTripDto dto) {
        this.userPartner = dto.getUserPartner();
        this.placeId = dto.getPlaceId();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.state = dto.getState();
        this.transportation = dto.getTransportation();
        this.title = dto.getTitle();
    }
}
