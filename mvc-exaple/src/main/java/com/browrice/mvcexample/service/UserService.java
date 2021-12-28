package com.browrice.mvcexample.service;

import com.browrice.mvcexample.domain.user.User;
import com.browrice.mvcexample.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 새로운 유저 등록
    public Long create(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    // 중복검사
    private void validateDuplicateUser(User user) {
        userRepository.findByName(user.getName())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 유저입니다.");
                });
    }

    // 전체 조회
    public List<User> findRices(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }
}
