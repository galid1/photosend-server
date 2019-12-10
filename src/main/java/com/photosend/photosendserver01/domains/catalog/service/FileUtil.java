package com.photosend.photosendserver01.domains.catalog.service;

import com.photosend.photosendserver01.domains.catalog.infra.ImageType;

public interface FileUtil {
    String getFileUploadPathForProductEntity(String filePath);
    String makeFileUploadPath(String fileName, ImageType imageType);
    void uploadFile(String fileName, byte[] fileByteArray);
    void deleteFile(String filePath);
}
