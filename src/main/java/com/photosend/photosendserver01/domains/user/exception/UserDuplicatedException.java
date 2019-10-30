package com.photosend.photosendserver01.domains.user.exception;

public class UserDuplicatedException extends RuntimeException{
    public UserDuplicatedException(String message) {
        super(message);
    }
}
