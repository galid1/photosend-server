package com.photosend.photosendserver01.domains.catalog.infra;

public enum ImageType {
    PRODUCT("product"), TICKET("tickets");

    final private String value;

    ImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
