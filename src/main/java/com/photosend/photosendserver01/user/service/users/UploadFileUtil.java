package com.photosend.photosendserver01.user.service.users;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUtil {
    String uploadFile(Long userId, MultipartFile file);
}
