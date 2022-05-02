package com.startrip.codebase.domain.favorite_event;

import com.startrip.codebase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavoriteEventRepository extends JpaRepository<FavoriteEvent, UUID> {
    List<FavoriteEvent> findAllByUserId (User user);

}
