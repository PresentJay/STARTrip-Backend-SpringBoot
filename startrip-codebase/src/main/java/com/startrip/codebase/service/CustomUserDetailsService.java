package com.startrip.codebase.service;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        // 해당 유저가 DB에 등록 됐는지 검사
        if (user.isEmpty()) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }

        // 스프링 시큐리티가 제공하는 user 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .roles(user.get().getAuthorities().toString())
                .build();
    }

}
