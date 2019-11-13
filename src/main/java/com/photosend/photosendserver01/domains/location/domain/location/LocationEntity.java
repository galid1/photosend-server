package com.photosend.photosendserver01.domains.location.domain.location;

import lombok.*;

import javax.persistence.*;

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

    @Embedded
    private Location location;

    @Builder
    public LocationEntity(String name, String locationImagePath, Location location) {
        this.name = name;
        this.locationImagePath = locationImagePath;
        this.location = location;
    }
}

