package com.photosend.photosendserver01.domains.user.domain.exception;

public class UserDuplicatedException extends RuntimeException{
    public UserDuplicatedException(String message) {
        super(message);
    }
}
