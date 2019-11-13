package com.photosend.photosendserver01.domains.location.domain.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {
    @ElementCollection
    @CollectionTable(name = "location_edges"
            , joinColumns = @JoinColumn(name = "location_id")
            , uniqueConstraints = @UniqueConstraint(columnNames = {"location_id", "a_edge"}))
    private List<PieceOfEdges> edges = new ArrayList<>();
}
