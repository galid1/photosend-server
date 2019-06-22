package com.photosend.photosendserver01.user.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUtil {
    String makeFileUploadPath(Long userId, String fileName);
    void uploadFile(String uploadPath, MultipartFile file);
}
