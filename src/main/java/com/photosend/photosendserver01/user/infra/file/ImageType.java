package com.photosend.photosendserver01.user.infra.file;

public enum ImageType {
    CLOTHES("clothes"), TICKET("ticket");

    final private String value;

    ImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
