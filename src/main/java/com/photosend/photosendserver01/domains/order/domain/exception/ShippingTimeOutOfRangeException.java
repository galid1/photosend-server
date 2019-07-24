package com.photosend.photosendserver01.domains.order.domain.exception;

public class ShippingTimeOutOfRangeException extends RuntimeException{
    public ShippingTimeOutOfRangeException(String message) {
        super(message);
    }
}
