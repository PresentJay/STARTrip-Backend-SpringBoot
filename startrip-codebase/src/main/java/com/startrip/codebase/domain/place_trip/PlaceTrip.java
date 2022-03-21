package com.startrip.codebase.domain.place_trip;

import com.startrip.codebase.dto.place_trip.UpdatePlaceTripDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlaceTrip {
    @Id
    @Column(name = "trip_id")
    private UUID tripId = UUID.randomUUID();

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_partner")
    private String userPartner;

    @Column(name = "place_id")
    private UUID placeId = UUID.randomUUID();

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private String state;

    private String transportation;

    private String title;

    public void update(UpdatePlaceTripDto dto) {
        this.tripId = dto.getTripId();
        this.userId = dto.getUserId();
        this.userPartner = dto.getUserPartner();
        this.placeId = dto.getPlaceId();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.state = dto.getState();
        this.transportation = dto.getTransportation();
        this.title = dto.getTitle();
    }
}
