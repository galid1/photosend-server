package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ClothesFullInformation {
    @NotNull
    @JsonProperty("image-path")
    private String clothImagePath;
    // Optional Field
    private FoundClothesInformation foundClothesInformation;
}
