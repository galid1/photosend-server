package com.photosend.photosendserver01.common.util.email;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface EmailUtil {
    void sendEmailWithImages(String userName, MultipartFile[] multipartFiles);
    void sendEmailWithImage(String userName, MultipartFile multipartFile);
}
