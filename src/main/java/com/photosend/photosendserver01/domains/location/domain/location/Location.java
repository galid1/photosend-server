package com.photosend.photosendserver01.domains.location.domain.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {
//    @ElementCollection
//    @CollectionTable(name = "location_edges"
//            , joinColumns = @JoinColumn(name = "location_id")
//            , uniqueConstraints = @UniqueConstraint(columnNames = {"location_id", "a_edge"}))
//    private List<PieceOfEdges> edges = new ArrayList<>();

    private double upperLeftLatitude;
    private double upperLeftLongitude;

    private double upperRightLatitude;
    private double upperRightLongitude;

    private double lowerLeftLatitude;
    private double lowerLeftLongitude;

    private double lowerRightLatitude;
    private double lowerRightLongitude;
}
