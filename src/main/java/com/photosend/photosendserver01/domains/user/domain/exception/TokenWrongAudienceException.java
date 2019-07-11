package com.photosend.photosendserver01.domains.user.domain.exception;

public class TokenWrongAudienceException extends RuntimeException{
    public TokenWrongAudienceException(String message) {
        super(message);
    }
}
