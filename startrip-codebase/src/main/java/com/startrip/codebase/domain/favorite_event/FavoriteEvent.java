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
    @GeneratedValue(generator = "UUID")
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


        public static FavoriteEvent of ( User user, Event event ){
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
        this.isExecuted = dto.getIsExecuted();
        this.updatedDate = LocalDateTime.now();
    }

    public void offValid(){
        this.isValid = false;
        this.updatedDate = LocalDateTime.now();
    }

}
