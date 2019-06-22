package com.photosend.photosendserver01.user.presentation;

import com.photosend.photosendserver01.user.domain.Size;

import javax.validation.constraints.NotNull;

public class Clothes {
    // Required Field
    @NotNull
    private String clothImagePath;

    // Optional Field
    private String name;
    private Integer price;
    private String brand;
    private Size size;
}
