package com.startrip.codebase.service.trip;

import com.startrip.codebase.domain.place_trip.PlaceTrip;
import com.startrip.codebase.domain.place_trip.PlaceTripRepository;
import com.startrip.codebase.domain.state.State;
import com.startrip.codebase.domain.state.StateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlaceTripServiceTest {
    @Autowired
    private PlaceTripService placeTripService;

    @Autowired
    private PlaceTripRepository placeTripRepository;

    @Autowired
    private StateRepository stateRepository;

    private void createPlaceTrip(State state) {
        PlaceTrip placeTrip = PlaceTrip.builder()
                .tripId(UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429"))
                .userId(Long.valueOf("123"))
                .userPartner("a")
                .placeId(UUID.randomUUID())
                .startTime(Date.valueOf("2022-03-23"))
                .endTime(Date.valueOf("2022-03-25"))
                .state(state)
                .transportation("버스")
                .title("김해 여행")
                .build();
        placeTripRepository.save(placeTrip);
    }

    @DisplayName("PlaceTrip과 State 매핑 확인")
    @Test
    public void placetrip_state() {
        State state = State.builder()
                .stateId(UUID.randomUUID())
                .stateNum(0)
                .build();
        stateRepository.save(state);

        createPlaceTrip(state);

        PlaceTrip find = placeTripService.getPlaceTrip(UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429"));

        assertThat(find.getState().getStateId().equals(UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429")));
        assertThat(find.getState().getStateNum().compareTo(0));
    }
}
