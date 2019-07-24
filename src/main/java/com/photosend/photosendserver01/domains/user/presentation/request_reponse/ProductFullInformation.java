package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductFullInformation {
    @NotNull
    private String productImagePath;
    // Optional Field
    private FoundProductInformation foundProductInformation;
}
