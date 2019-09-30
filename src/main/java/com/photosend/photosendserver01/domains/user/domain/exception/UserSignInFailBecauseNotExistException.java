package com.photosend.photosendserver01.domains.user.domain.exception;

public class UserSignInFailBecauseNotExistException extends RuntimeException{
    private String weChatOpenId;

    public UserSignInFailBecauseNotExistException(String message, String weChatOpenId) {
        super(message);
        this.weChatOpenId = weChatOpenId;
    }

    public String getWeChatOpenId() {
        return this.weChatOpenId;
    }
}
