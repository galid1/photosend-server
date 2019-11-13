package com.photosend.photosendserver01.domains.location.domain.location;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PieceOfEdges {
    @Enumerated(value = EnumType.STRING)
    @Column(name = "a_edge")
    private AEdge aEdge;

    private double latitude;
    private double longitude;
}
