package com.startrip.codebase.dto.favoriteEvent;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateFavoriteE {

    private UUID favoriteEventId;
    private Boolean isExecuted;

}