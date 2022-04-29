package com.startrip.codebase.domain.favorite_event;

import com.startrip.codebase.domain.event.Event;
import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.dto.favoriteEvent.RequestFavoriteE;
import com.startrip.codebase.dto.favoriteEvent.UpdateFavoriteE;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FavoriteEvent {

    @Id
    @Column(name = "favoriteevent_id")
    private UUID favoriteEventId;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id") // 0416기준, Long
    private User userId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id")  //0416기준,  Long
    private Event eventId;

    private Boolean isValid;
    private Boolean isExecuted;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static FavoriteEvent of (User user, Event event ){
        // TODO: requestDTO 가 필요없는 게 맞는지 확인해야한다
        FavoriteEvent favoriteEvent = FavoriteEvent.builder()
                .userId(user)
                .eventId(event)
                .createdDate(LocalDateTime.now())
                .isValid(true)
                .isExecuted(false)
                .build();

        return favoriteEvent;
    }

    public void update(UpdateFavoriteE dto){
        this.isValid = dto.getIsValid(); //TODO: state는 기간에 따라 0, 1, 삭제 작동하도록 작성해야 한다
        this.updatedDate = LocalDateTime.now();
    }

}
