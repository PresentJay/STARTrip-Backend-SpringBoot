package com.startrip.codebase.domain.place;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Place {
    @Id
    private UUID placeId;

    @NotNull
    private String address;

    private String placeName;

    private String placeInfo;

    private String placePhoto;

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> categoryList;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Double averageViewTime;
}
