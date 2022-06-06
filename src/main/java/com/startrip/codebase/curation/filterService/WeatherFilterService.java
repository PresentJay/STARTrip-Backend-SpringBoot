package com.startrip.codebase.curation.filterService;

import com.startrip.codebase.curation.CurationChain;
import com.startrip.codebase.curation.CurationObject;
import com.startrip.codebase.curation.chains.ChainType;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;
import com.startrip.codebase.domain.place.QPlace;
import com.startrip.codebase.util.weather.WeatherApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class WeatherFilterService implements CurationChain<CurationObject, CurationObject> {


    private final PlaceRepository placeRepository;
    private WeatherApi weatherapi;

    @Autowired
    public WeatherFilterService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    private final List<Place> resultPlaces = new ArrayList<>();
    private String weatherScore;
    private String userWeather;

    @Override
    public CurationObject process(CurationObject input) {
        Object object = input.userInput.get(ChainType.WEATHER);
        if (object instanceof String) {
            userWeather = (String) object;
            setWeatherPlace();  // key: 지역명, value: 지역별 오늘자 날씨판별
            for(Place place:resultPlaces){
                UUID placeId = place.getId();
                input.booleanBuilder.or(QPlace.place.id.eq(placeId));
            }
        }
        return input;
    }

    public void setWeatherPlace() {
        List<Place> places = placeRepository.findAll();
        for (Place place : places) {
            String x = String.valueOf(place.getLatitude());
            String y;
            try{
                y = String.valueOf(place.getLongitude());
            } catch(NullPointerException e){
                y = "0.000000";
            }

            try {
                weatherScore = weatherapi.getApiResult(x, y);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                continue;
            }

            if (weatherScore.equals(userWeather)) {
                resultPlaces.add(place);
            }
        }
    }
}