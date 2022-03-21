package com.startrip.codebase.domain.place;

import com.startrip.codebase.domain.category.Category;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "category_id")
    private UUID eventId = UUID.randomUUID();

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private String address;

    private String placePhoto;

    private String placeDescription;

    private String placeName;

    private Int phoneNumber;
}
