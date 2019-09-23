package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.infra.file.ImageType;
import org.springframework.web.multipart.MultipartFile;

public interface FileUtil {
    String makeFileUploadPath(Long userId, String fileName, ImageType imageType);
    void uploadFile(String uploadPath, MultipartFile file);
    void deleteFile(String filePath);
}
