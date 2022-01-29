package com.startrip.codebase.domain.user;

import com.startrip.codebase.constant.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
<<<<<<< HEAD
@Table(name = "\"User\"")
=======
@Table(name = "user")
>>>>>>> parent of 5e2ddd7 (Revert "complete the story #10")
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

<<<<<<< HEAD
    @Column(unique = true) // 중복 이메일이 안되도록 설정
=======
    @Column(unique = true)
>>>>>>> parent of 5e2ddd7 (Revert "complete the story #10")
    private String email;

    private String password;

    private String nickname;

    private String picture_url;

    private Boolean receive_email;

<<<<<<< HEAD
    @Enumerated(EnumType.STRING)
=======
    @Enumerated
>>>>>>> parent of 5e2ddd7 (Revert "complete the story #10")
    private Role authorities;
}
