package com.startrip.codebase.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 회원"),
    ADMIN("ROLE_ADMIN", "관리자"),
    EVENT_CREATOR("ROLE_EVENT_CREATOR", "이벤트 주최자");

    private final String key;
    private final String title;
}