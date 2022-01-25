package com.startrip.codebase.domain.user;

import com.startrip.codebase.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String picture_url;

    private Boolean receive_email;

    private Role role;
}
