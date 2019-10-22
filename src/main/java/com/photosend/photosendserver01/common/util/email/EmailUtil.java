package com.photosend.photosendserver01.common.util.email;

public interface EmailUtil {
    void sendEmailWithImage(String userName, byte[] imageFileBytes);
}
