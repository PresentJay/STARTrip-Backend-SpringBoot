package com.startrip.codebase.service;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        // 해당 유저가 DB에 등록 됬는지 검사
        if (user == null) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }

        // 스프링 시큐리티가 제공하는 user 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getAuthorities().toString())
                .build();
    }

}
