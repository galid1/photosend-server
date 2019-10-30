package com.photosend.photosendserver01.domains.order.exception;

public class NoOrderLineException extends RuntimeException{
    public NoOrderLineException(String message) {
        super(message);
    }
}
