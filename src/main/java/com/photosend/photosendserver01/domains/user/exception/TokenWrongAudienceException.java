package com.photosend.photosendserver01.domains.user.exception;

public class TokenWrongAudienceException extends RuntimeException{
    public TokenWrongAudienceException(String message) {
        super(message);
    }
}
