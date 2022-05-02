package com.startrip.codebase.service;


import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.favorite_event.FavoriteEvent;
import com.startrip.codebase.domain.favorite_event.FavoriteEventRepository;
import com.startrip.codebase.domain.operating_time.OperatingTime;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.favoriteEvent.RequestFavoriteE;
import com.startrip.codebase.dto.favoriteEvent.UpdateFavoriteE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FavoriteEventService {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private FavoriteEventRepository favoriteEventRepository;
    private static final Long DELETE_SEC = Long.valueOf(120); //2m
    private static LocalDateTime updateTime;

    @Autowired
    public FavoriteEventService(FavoriteEventRepository favoriteEventRepository,
                                EventRepository eventRepository,
                                UserRepository userRepository){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.favoriteEventRepository = favoriteEventRepository;
    }

    @Transactional
    public void createFavoriteEvent (RequestFavoriteE dto){
       /* User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> {
                   throw new IllegalStateException("존재하지 않는 유저입니다");
                });*/
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> {
                    throw new IllegalStateException("존재하지 않는 이벤트입니다");
                });
        // FavoriteEvent favoriteEvent = FavoriteEvent.of(user, event);
        FavoriteEvent favoriteEvent = FavoriteEvent.of(event);

        favoriteEventRepository.save(favoriteEvent);
    }

    public List<FavoriteEvent> getFavoriteEvent (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new IllegalStateException("존재하지 않는 유저입니다");
                });
        Optional<List<FavoriteEvent>> favoriteEvents = Optional.ofNullable(favoriteEventRepository.findAllByUserId(userId));
        if(favoriteEvents.isEmpty()){
            throw new RuntimeException("해당 user에 존재하는 좋아요이벤트 정보가 없습니다");
        }
        return favoriteEvents.get();
    }

    public void updateFavoriteEvent (UpdateFavoriteE dto){
        FavoriteEvent favoriteEvent = favoriteEventRepository.findById(dto.getFavoriteEventId())
                .orElseThrow( () -> new RuntimeException("해당 이벤트좋아요는 존재하지 않습니다"));
        favoriteEvent.update(dto);
        favoriteEventRepository.save(favoriteEvent);

    }

    public void deleteFavoriteEvent (UUID fEventId){

        // TODO: 유저가 보내는 삭제요청이다
        // TODO: 시스템이 24h 기준으로 모니터링 하여 해당 데이터(garbage)삭제하도록 해야한다
        FavoriteEvent favoriteEvent = favoriteEventRepository.findById(fEventId)
                .orElseThrow( () -> new RuntimeException("해당 이벤트좋아요는 존재하지 않습니다"));
        favoriteEvent.offValid();
        favoriteEventRepository.save(favoriteEvent);
        updateTime = favoriteEvent.getUpdatedDate();

        try {
            Long diffSeconds = moniteringOffValidItem();
            if (diffSeconds > DELETE_SEC) {
                favoriteEventRepository.deleteById(favoriteEvent.getFavoriteEventId());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "1 * * * * ?") // 1분마다
    public static long moniteringOffValidItem ( ) {
        LocalDateTime currentTime = LocalDateTime.now();
        Long diffSeconds = ChronoUnit.SECONDS.between(currentTime, updateTime);
        return diffSeconds;

    }
}
