package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductFullInformation {
    @NotNull
    @JsonProperty("image-path")
    private String productImagePath;
    // Optional Field
    private FoundProductInformation foundProductInformation;
}
