package com.startrip.codebase.dto.favoriteEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseFavoriteE {

    private UUID favoriteEventId;
    private Long userId;
    private Long eventId;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private boolean isExecuted;
}
