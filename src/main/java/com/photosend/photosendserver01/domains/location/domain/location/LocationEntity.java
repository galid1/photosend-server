package com.photosend.photosendserver01.domains.location.domain.location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "location")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private long locationId;

    private String name;

    @Column(name = "location_image_path")
    private String locationImagePath;

    @ElementCollection
    @CollectionTable(name = "location_list", joinColumns = @JoinColumn(name = "location_id"))
    private List<Location> locationList = new ArrayList<>();

    @Builder
    public LocationEntity(String name, String locationImagePath, List<Location> locationList) {
        this.name = name;
        this.locationImagePath = locationImagePath;
        this.locationList = locationList;
    }
}

