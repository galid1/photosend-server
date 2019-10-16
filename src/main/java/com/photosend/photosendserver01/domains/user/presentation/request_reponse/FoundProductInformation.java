package com.photosend.photosendserver01.domains.user.presentation.request_reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photosend.photosendserver01.domains.user.domain.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FoundProductInformation {
    private Long pid;
    private String name;
    private Integer price;
    private String brand;
    private String size;
//    private List<Size> size = new ArrayList<>();
}
