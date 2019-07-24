package com.photosend.photosendserver01.domains.order.domain.exception;

public class NoOrderLineException extends RuntimeException{
    public NoOrderLineException(String message) {
        super(message);
    }
}
