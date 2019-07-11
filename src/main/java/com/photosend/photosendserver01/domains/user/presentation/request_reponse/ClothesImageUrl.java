package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photosend.photosendserver01.domains.user.domain.ClothesEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ClothesImageUrl {
    @JsonProperty("clothes-id")
    private Long cid;
    @JsonProperty("image-path")
    private String clothesImageUrl;

    public ClothesEntity toEntity() {
        return ClothesEntity.builder()
                .clothesImagePath(this.clothesImageUrl)
                .build();
    }
}
