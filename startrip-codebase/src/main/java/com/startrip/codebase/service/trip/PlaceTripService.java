package com.startrip.codebase.service.trip;

import com.startrip.codebase.domain.place_trip.PlaceTrip;
import com.startrip.codebase.domain.place_trip.PlaceTripRepository;
import com.startrip.codebase.dto.place_trip.CreatePlaceTripDto;
import com.startrip.codebase.dto.place_trip.ResponsePlaceTripDto;
import com.startrip.codebase.dto.place_trip.UpdatePlaceTripDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PlaceTripService {
    private final PlaceTripRepository placeTripRepository;

    @Autowired
    public PlaceTripService(PlaceTripRepository placeTripRepository) {
        this.placeTripRepository = placeTripRepository;
    }

    // Create
    public void createPlaceTrip(CreatePlaceTripDto dto) {

        PlaceTrip placeTrip = PlaceTrip.builder()
                .tripId(dto.getTripId())
                .userId(dto.getUserId())
                .userPartner(dto.getUserPartner())
                .placeId(dto.getPlaceId())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .state(dto.placeTripState())
                .transportation(dto.getTransportation())
                .title(dto.getTitle())
                .build();
        placeTripRepository.save(placeTrip);
        log.info(placeTrip.toString());
    }

    // All
    public List<ResponsePlaceTripDto> allPlaceTrip() {
        List<PlaceTrip> placeTrips = placeTripRepository.findAll();
        List<ResponsePlaceTripDto> dtos = new ArrayList<>();

        for (PlaceTrip placeTrip : placeTrips) {
            ResponsePlaceTripDto dto = new ResponsePlaceTripDto();
            dto.setTripId(placeTrip.getTripId());
            dto.setPlaceId(placeTrip.getPlaceId());
            dto.setTitle(placeTrip.getTitle());

            dtos.add(dto);
        }
        return dtos;
    }


    // Get
    public PlaceTrip getPlaceTrip(UUID id){
        return placeTripRepository.findById(id).get();
    }

    // Update
    public void updatePlaceTrip(UUID id, UpdatePlaceTripDto dto){
        Optional<PlaceTrip> placeTrip = placeTripRepository.findById(id);
        if (placeTrip.isEmpty()){
            throw new RuntimeException("존재하지 않는 Place Trip 입니다.");
        }
        PlaceTrip use = placeTrip.get();

        use.update(dto);

        placeTripRepository.save(use);
    }

    // Delete
    public void deletePlaceTrip(UUID id) {
        placeTripRepository.deleteById(id);
    }
}
