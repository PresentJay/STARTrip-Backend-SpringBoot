package com.startrip.codebase.domain.user;


import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static Map<Long, User> store = new HashMap<>();
    private static long sequnce = 0L;

    @Override
    public User save(User user) {
        user.setUserId(++sequnce);
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
