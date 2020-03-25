package com.photosend.photosendserver01;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class TestModel {
    private String date;
    private String from;
    private String to;
    private String message;
}
