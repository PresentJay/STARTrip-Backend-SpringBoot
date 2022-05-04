package com.startrip.codebase.dto.favoriteEvent;

import com.startrip.codebase.domain.favorite_event.FavoriteEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ResponseFavoriteEventDto {

    private UUID favoriteEventId;
    private Long userId;
    private UUID eventId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isExecuted;
    private Boolean isValid;

    public static ResponseFavoriteEventDto of(FavoriteEvent favoriteEvent){
        ResponseFavoriteEventDto responseFavoriteEventDto = new ResponseFavoriteEventDto();
        responseFavoriteEventDto.setFavoriteEventId(favoriteEvent.getFavoriteEventId());
        responseFavoriteEventDto.setUserId(favoriteEvent.getUserId().getUserId());
        responseFavoriteEventDto.setEventId(favoriteEvent.getEventId().getEventId());
        responseFavoriteEventDto.setCreatedDate(favoriteEvent.getCreatedDate());
        responseFavoriteEventDto.setUpdatedDate(favoriteEvent.getUpdatedDate());
        responseFavoriteEventDto.setIsExecuted(favoriteEvent.getIsExecuted());
        responseFavoriteEventDto.setIsValid(favoriteEvent.getIsValid());

        return responseFavoriteEventDto;
    }
}
