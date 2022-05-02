package com.startrip.codebase.service;
import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.event.EventRepository;
import com.startrip.codebase.domain.favorite_event.FavoriteEvent;
import com.startrip.codebase.domain.favorite_event.FavoriteEventRepository;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import com.startrip.codebase.dto.favoriteEvent.RequestFavoriteE;
import com.startrip.codebase.dto.favoriteEvent.UpdateFavoriteE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class FavoriteEventService {

    private static UserRepository userRepository;
    private static EventRepository eventRepository;
    private static FavoriteEventRepository favoriteEventRepository;

    private static final Long DELETE_SEC = Long.valueOf(120); //2m
    private static List<FavoriteEvent> deleteFEvents = new LinkedList<>();

    @Autowired
    public FavoriteEventService(FavoriteEventRepository favoriteEventRepository,
                                EventRepository eventRepository,
                                UserRepository userRepository){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.favoriteEventRepository = favoriteEventRepository;
    }

    @Transactional
    public void createFavoriteEvent (Long userId, RequestFavoriteE dto){
       User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                   throw new IllegalStateException("존재하지 않는 유저입니다");
                });
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> {
                    throw new IllegalStateException("존재하지 않는 이벤트입니다");
                });

        FavoriteEvent favoriteEvent;
        Optional<FavoriteEvent> FEventFindByEvent = Optional.ofNullable(favoriteEventRepository.findByEventIdAndUserId(event, user));
        if(FEventFindByEvent.isPresent()){
            favoriteEvent = FEventFindByEvent.get();
            favoriteEvent.onValid();
        }else{
            favoriteEvent = FavoriteEvent.of(user, event);
            favoriteEventRepository.save(favoriteEvent);
        }

    }

    public List<FavoriteEvent> getFavoriteEvent (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new IllegalStateException("존재하지 않는 유저입니다"); });

        Optional<List<FavoriteEvent>> favoriteEvents = Optional.ofNullable(favoriteEventRepository.findAllByUserId(user));
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

        FavoriteEvent deleteFEvent = favoriteEventRepository.findById(fEventId)
                .orElseThrow( () -> new RuntimeException("해당 이벤트좋아요는 존재하지 않습니다"));

        deleteFEvent.offValid();
        favoriteEventRepository.save(deleteFEvent);
        deleteFEvents.add(deleteFEvent);
    }

    @Scheduled(fixedRate = 1000*60)
    public void deleteFEventJob(){ // 1분마다

        if( !deleteFEvents.isEmpty()) {
            log.info("스케쥴 작업 시작, 리스트에 담긴 아이템 확인중");
            for (Iterator<FavoriteEvent> iter = deleteFEvents.iterator(); iter.hasNext();) {
                FavoriteEvent deleteFEvent  = iter.next();
                if (!deleteFEvent.getIsValid()) timeOver_deleteItem(deleteFEvent);
                else iter.remove();
            }
        }
    } // 1분마다

    public static void timeOver_deleteItem (FavoriteEvent deleteFEvent) {

        LocalDateTime currentTime = LocalDateTime.now();
        Long diffSeconds = ChronoUnit.SECONDS.between(deleteFEvent.getUpdatedDate(), currentTime);

        if (diffSeconds > DELETE_SEC) {
            favoriteEventRepository.deleteById(deleteFEvent.getFavoriteEventId());
            log.info("삭제완료");
            deleteFEvents.remove(deleteFEvent);
        }
    }
}
