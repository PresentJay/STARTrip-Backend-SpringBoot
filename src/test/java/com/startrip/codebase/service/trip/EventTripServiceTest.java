package com.startrip.codebase.service.trip;

import com.startrip.codebase.domain.event_trip.EventTrip;
import com.startrip.codebase.domain.event_trip.EventTripRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class EventTripServiceTest {
    @Autowired
    private EventTripService eventTripService;

    @Autowired
    private EventTripRepository eventTripRepository;

    @Autowired
    private UserRepository userRepository;

    private final UUID eventTripId = UUID.randomUUID();

    public void cleanUp() {
        userRepository.deleteAllInBatch();
    }

    private void createEventTrip(User user) {
        EventTrip placeTrip = EventTrip.builder()
                .tripId(eventTripId)
                .userId(user)
                .userPartner("a")
                .eventId(UUID.randomUUID())
                .startTime(Date.valueOf("2022-03-23"))
                .endTime(Date.valueOf("2022-03-25"))
                .state(1)
                .transportation("버스")
                .title("김해 여행")
                .build();
        eventTripRepository.save(placeTrip);
    }

    @DisplayName("EventTrip과 User 매핑 확인")
    @Test
    public void eventtrip_user() {
        cleanUp();
        User user = User.builder()
                .name("c")
                .email("3@3.com")
                .build();
        userRepository.save(user);

        createEventTrip(user);

        EventTrip find = eventTripService.getEventTrip(eventTripId);

        assertThat(find.getUserId().getName().compareTo("c"));
        assertThat(find.getUserId().getEmail().compareTo("3@3"));
        cleanUp();
    }
}
