package com.startrip.codebase.domain.place;

import com.startrip.codebase.domain.category.Category;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Place {

    @Id
    @Column(name = "place_id")
    private UUID placeId = UUID.randomUUID();

    @NotNull
    private String address;

    private String placeName;

    private String placeInfo;

    private String placePhoto;

    @ElementCollection
    @CollectionTable(name = "category_list")
    private List<Integer> categorylist;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Column(name = "average_view_time")
    private Double averageViewTime;
}
