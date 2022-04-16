package com.startrip.codebase.domain.place_trip;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.place_trip.UpdatePlaceTripDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userId;

    @Column(name = "user_partner")
    private String userPartner;

    @Column(name = "place_id")
    private UUID placeId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private Integer state;

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
