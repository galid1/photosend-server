package com.photosend.photosendserver01.domains.user.domain.exception;

public class ProductNotFoundException extends IllegalArgumentException {
    public ProductNotFoundException(String s) {
        super(s);
    }
}
