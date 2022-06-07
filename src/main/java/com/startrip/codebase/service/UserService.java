package com.startrip.codebase.service;

import com.startrip.codebase.domain.user.User;
import com.startrip.codebase.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 새로운 유저 등록
    public Long create(User user) {
        validateDuplicateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encoding
        userRepository.save(user);
        return user.getUserId();
    }

    // 중복검사
    private void validateDuplicateUser(User user) {
        Optional<User> savedUser = userRepository.findByEmail(user.getEmail());
        if (savedUser.isPresent()) {
            throw new RuntimeException("존재하는 유저입니다.");
        }

    }

    // 전체 조회
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }

}
