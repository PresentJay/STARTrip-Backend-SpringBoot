package com.startrip.codebase.domain.event_pricing;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class EventPricingRepositoryTest {
    @Autowired
    private EventPricingRepository eventPricingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User loadUserAdmin() {
        return userRepository.findByEmail("admin@admin.com").get();
    }

    private Event createEvent() {
        Event event = Event.builder()
                .eventTitle("테스트 이벤트")
                .description("이벤트 내용")
                .contact("내 전화번호")
                .build();
        eventRepository.save(event);
        return event;
    }

    @DisplayName("이벤트의 가격정책 저장 테스트")
    @Test
    public void test0() throws Exception {
        Event event = createEvent();
        EventPricing eventPricing = EventPricing.builder()
                .id(UUID.randomUUID())
                .range("1만원 ~ 2만원")
                .content("성인 2만원, 학생 1만원")
                .event(event)
                .createdTime(LocalDateTime.now())
                .build();
        eventPricingRepository.save(eventPricing);

        assertEquals(eventPricing.getContent(), eventPricingRepository.findAllByEvent_EventId(event.getEventId()).get(0).getContent());
    }

}