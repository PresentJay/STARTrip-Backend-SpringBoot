package com.startrip.codebase.service.trip;

import com.startrip.codebase.domain.event_trip.EventTrip;
import com.startrip.codebase.domain.event_trip.EventTripRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EventTripServiceTest {
    @Autowired
    private EventTripService eventTripService;

    @Autowired
    private EventTripRepository eventTripRepository;

    @Autowired
    private UserRepository userRepository;

    private void createEventTrip(User user) {
        EventTrip placeTrip = EventTrip.builder()
                .tripId(UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429"))
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
        User user = User.builder()
                .name("a")
                .email("1@1.com")
                .build();
        userRepository.save(user);

        createEventTrip(user);

        EventTrip find = eventTripService.getEventTrip(UUID.fromString("e3661498-9473-4c06-9d52-464cc2f59429"));

        assertThat(find.getUserId().getName().compareTo("a"));
        assertThat(find.getUserId().getEmail().compareTo("1@1"));
    }
}
