package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.infra.file.ImageType;

public interface FileUtil {
    String makeFileUploadPath(String fileName, ImageType imageType);
    void uploadFile(String uploadPath, byte[] fileByteArray);
    void deleteFile(String filePath);
}
