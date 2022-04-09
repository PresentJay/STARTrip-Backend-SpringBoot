package com.startrip.codebase.domain.place_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceInfoRepository extends JpaRepository<PlaceInfo, Long> {
}
