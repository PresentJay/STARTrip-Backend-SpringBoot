package com.startrip.codebase.domain.Operating_time;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperatingTimeRepository extends JpaRepository<OperatingTime, Long> {


    List<OperatingTime> findAllByPlaceId(Long placeId);


}
