package com.photosend.photosendserver01.common.util.email;

import java.util.List;

public interface EmailUtil {
    void sendEmail(EmailType type, long userId, List<byte[]> imageFileByteArrList);
}
