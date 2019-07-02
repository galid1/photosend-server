package com.photosend.photosendserver01.user.infra.file;

import com.photosend.photosendserver01.user.service.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtilImpl implements FileUtil {
    @Override
    public String makeFileUploadPath(Long userId, String fileName, ImageType fileType) {
        return System.getProperty("user.home") + "/" + fileType.getValue() + "/" + String.valueOf(userId) + "_" + makeRandomFileName(fileName);
    }

    private String makeRandomFileName(String originFileName) {
        return UUID.randomUUID() + "_" + originFileName;
    }

    @Override
    public void uploadFile(String uploadPath, MultipartFile file) {
        File destinationFile = new File(uploadPath);
        if (!destinationFile.getParentFile().exists())
            destinationFile.getParentFile().mkdir();
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }
}
