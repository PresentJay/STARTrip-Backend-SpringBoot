package com.startrip.codebase.domain.Operating_time;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperatingTimeRepository extends JpaRepository<OperatingTime, UUID> {


    List<OperatingTime> findAllByPlaceId(Long placeId);


}
