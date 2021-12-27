package com.browrice.mvcexample.repository;

import com.browrice.mvcexample.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByName(String origin);

    List<User> findAll();
}
