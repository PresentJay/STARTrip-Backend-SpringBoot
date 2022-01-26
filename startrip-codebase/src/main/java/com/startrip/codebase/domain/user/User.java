package com.startrip.codebase.domain.user;

import com.startrip.codebase.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "\"User\"")
@Getter
@Setter
@ToString
public class User {

    private Long id;

    @Column(unique = true) // 중복 이메일이 안되도록 설정
    private String email;

    private String password;

    private String nickname;

    private String picture_url;

    private Boolean receive_email;

    @Enumerated(EnumType.STRING)
    private Role authorities;
}
