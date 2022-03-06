package com.startrip.codebase.domain.auth.dto;

import com.startrip.codebase.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// TODO 토큰으로 대체
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture_url();
    }
}
