package com.photosend.photosendserver01.user.domain.exception;

public class TokenWrongAudienceException extends RuntimeException{
    public TokenWrongAudienceException(String message) {
        super(message);
    }
}
