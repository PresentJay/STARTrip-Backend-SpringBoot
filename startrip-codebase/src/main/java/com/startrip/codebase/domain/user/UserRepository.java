package com.startrip.codebase.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String origin);

    List<User> findAll();
}
