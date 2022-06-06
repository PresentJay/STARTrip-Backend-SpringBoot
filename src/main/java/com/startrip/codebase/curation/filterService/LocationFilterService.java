package com.startrip.codebase.curation.filterService;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.CurationChain;
import com.startrip.codebase.curation.CurationObject;
import com.startrip.codebase.curation.chains.ChainType;
import com.startrip.codebase.curation.curationDto.ResponseLocationDto;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.QPlace;
import com.startrip.codebase.util.GoogleGeocodeApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.querydsl.core.types.dsl.MathExpressions.*;

@Slf4j
@Service
public class LocationFilterService implements CurationChain<CurationObject, CurationObject> {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    Double [] userInputLocate = new Double [2];

    @Override
    public CurationObject process(CurationObject input) {
        Object object = input.userInput.get(ChainType.LOCATION);
        if (object instanceof Double []){
            userInputLocate= (Double []) object;
            NumberExpression<Double> distanceFormula = getDistanceFormula(userInputLocate[0], userInputLocate[1]);
            input.booleanBuilder.and(distanceFormula.lt(2));
        }
        return input;
    }

    public List<ResponseLocationDto> getPlaceList(Double [] input) {
        List<ResponseLocationDto> responsePlaceList= new ArrayList<>();

        NumberExpression<Double> distanceFormula = getDistanceFormula(input[0], input[1]);
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(distanceFormula.lt(2));
        JPAQuery<Place> query = new JPAQuery<>();

        QPlace qPlace = QPlace.place;
        List<Place> placeList =
                jpaQueryFactory.select(qPlace)
                        .from(qPlace)
                        .where(whereClause)
                        .fetch();

        for (Place place : placeList) {
            Double [] currentPlaceLocation = new Double [2];
            currentPlaceLocation[0] = place.getLatitude();
            currentPlaceLocation[1] = place.getLongitude();

            Double distance = getDistance(input, currentPlaceLocation);
            //log.info("계산된 거리:" + distance);

            ResponseLocationDto dto = new ResponseLocationDto();
            dto.setPlaceName(place.getPlaceName());
            dto.setLatitude(place.getLatitude());
            dto.setLongitude(place.getLongitude());
            dto.setPlaceAddress(place.getAddress());
            dto.setDistance(distance);

            responsePlaceList.add(dto);
        }

        return responsePlaceList;
    }

    public Double getDistance(Double [] input, Double [] current){
        double distance;
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        double deltaLatitude = Math.abs(input[0] - current[0]) * toRadian;
        double deltaLongitude = Math.abs(input[1] - current[1]) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(input[0] * toRadian) * Math.cos(current[0] * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * Math.asin(squareRoot);

        return distance;
    }

    // TODO: Address To Locate Info
    /* public void convertAddressToLocate(String address){
        GoogleGeocodeApi googleGeocodeApi = new GoogleGeocodeApi();
        Map<String, String> result = new HashMap<>();
        result = googleGeocodeApi.getGeoDataByAddress(address);

        userInputLocate[0] = Double.parseDouble(result.get("lat"));
        userInputLocate[1] = Double.parseDouble(result.get("lng"));
    } */


    public NumberExpression<Double> getDistanceFormula(Double inputLatitude, Double inputLongitude) {

        QPlace qPlace = QPlace.place;
        NumberPath<Double> lat = qPlace.latitude;
        NumberPath<Double> lng = qPlace.longitude;

        NumberExpression<Double> formula = (acos
                (cos(radians(lat))
                        .multiply(cos(radians(Expressions.constant(inputLatitude))))
                        .multiply(cos(radians(Expressions.constant(inputLongitude))
                                        .subtract(radians(lng))
                                )
                        )
                        .add(sin(radians(lat))
                                .multiply(sin(radians(Expressions.constant(inputLatitude))))
                        )
                )
        ).multiply(Expressions.constant(6371));

        return formula;
    }
}


