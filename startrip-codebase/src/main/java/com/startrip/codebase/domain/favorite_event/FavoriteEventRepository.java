package com.startrip.codebase.domain.favorite_event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FavoriteEventRepository extends JpaRepository<FavoriteEvent, UUID> {

}
