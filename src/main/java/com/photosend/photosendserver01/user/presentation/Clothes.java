package com.photosend.photosendserver01.user.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photosend.photosendserver01.user.domain.Size;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clothes {
    // Required Field
    @NotNull
    @JsonProperty("image_path")
    private String clothImagePath;

    // Optional Field
    private String name;
    private Integer price;
    private String brand;
    private Size size;
}
