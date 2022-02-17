package com.startrip.codebase.domain.user;

import com.startrip.codebase.constant.Role;
import com.startrip.codebase.dto.SignUpDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "\"User\"")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true) // 중복 이메일이 안되도록 설정
    private String email;

    private String password;

    private String nickname;

    private String picture_url;

    private Boolean receive_email;

    @Enumerated(EnumType.STRING)
    private Role authorities;

    public static User createUser(SignUpDto signUpDto) {
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .nickname("test")
                .authorities(Role.ROLE_USER)
                .build();
        return user;
    }
}
