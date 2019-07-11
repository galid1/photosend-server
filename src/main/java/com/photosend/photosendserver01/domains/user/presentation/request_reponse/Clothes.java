package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photosend.photosendserver01.domains.user.domain.Size;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Clothes {
    // Required Field
    @JsonProperty("clothes-id")
    private Long cid;
    @NotNull
    @JsonProperty("image-path")
    private String clothImagePath;

    // Optional Field
    private String name;
    private Integer price;
    private String brand;
    private Size size;
}
