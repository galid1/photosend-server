package com.photosend.photosendserver01.user.service;

import com.photosend.photosendserver01.user.infra.file.ImageType;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUtil {
    String makeFileUploadPath(Long userId, String fileName, ImageType imageType);
    void uploadFile(String uploadPath, MultipartFile file);
}
