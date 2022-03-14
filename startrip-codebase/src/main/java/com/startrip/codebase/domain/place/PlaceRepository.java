package com.startrip.codebase.domain.place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {

    //페이징
    Page<Place> findByPlaceName(String PlaceName, Pageable pageable);
    //페이징 ver2
    Page<Place> findAll(Pageable pageable);
}
