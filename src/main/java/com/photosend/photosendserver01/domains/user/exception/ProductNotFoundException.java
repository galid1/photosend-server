package com.photosend.photosendserver01.domains.user.exception;

public class ProductNotFoundException extends IllegalArgumentException {
    public ProductNotFoundException(String s) {
        super(s);
    }
}
