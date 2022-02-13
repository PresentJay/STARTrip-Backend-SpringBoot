package com.startrip.codebase.domain.place;

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
    private UUID place_id;

    @NotNull
    private String address;

    private String place_name;

    private String place_info;

    private String place_photo;

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> category_list;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Double average_view_time;
}
