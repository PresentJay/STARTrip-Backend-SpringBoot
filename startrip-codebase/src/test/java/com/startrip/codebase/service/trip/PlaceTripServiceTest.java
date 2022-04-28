package com.startrip.codebase.service.trip;

import com.startrip.codebase.domain.place_trip.PlaceTrip;
import com.startrip.codebase.domain.place_trip.PlaceTripRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class PlaceTripServiceTest {
    @Autowired
    private PlaceTripService placeTripService;

    @Autowired
    private PlaceTripRepository placeTripRepository;

    @Autowired
    private UserRepository userRepository;

    private final UUID placeTripId = UUID.randomUUID();

    public void cleanUp() {
        userRepository.deleteAllInBatch();
    }

    private void createPlaceTrip(User user) {
        PlaceTrip placeTrip = PlaceTrip.builder()
                .tripId(placeTripId)
                .userId(user)
                .userPartner("a")
                .placeId(UUID.randomUUID())
                .startTime(Date.valueOf("2022-03-23"))
                .endTime(Date.valueOf("2022-03-25"))
                .state(1)
                .transportation("버스")
                .title("김해 여행")
                .build();
        placeTripRepository.save(placeTrip);
    }

    @WithMockUser(roles = "USER")
    @DisplayName("PlaceTrip과 User 매핑 확인")
    @Test
    public void placetrip_user() {
        cleanUp();
        User user = User.builder()
                .name("d")
                .email("4@4.com")
                .build();
        userRepository.save(user);

        createPlaceTrip(user);

        PlaceTrip find = placeTripService.getPlaceTrip(placeTripId);

        assertThat(find.getUserId().getName().compareTo("d"));
        assertThat(find.getUserId().getEmail().compareTo("4@4"));
        cleanUp();
    }
}
