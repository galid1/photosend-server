package com.photosend.photosendserver01.domains.catalog.infra;

import com.photosend.photosendserver01.domains.catalog.service.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class UploadToLocalFileUtilImpl implements FileUtil {
    @Override
    public String getFileUploadPathForProductEntity(String filePath) {
        throw new UnsupportedOperationException("아직 지원되지 않습니다.");
    }

    @Override
    public String makeFileUploadPath(String originalFileName, ImageType fileType) {
        return System.getProperty("user.home") + "/" + fileType.getValue() + "/" + makeRandomFileName(originalFileName);
    }

    private String makeRandomFileName(String originFileName) {
        return UUID.randomUUID() + "_" + originFileName;
    }

    @Override
    public void uploadFile(String fileName, byte[] fileByteArray) {
        String uploadPath = makeFileUploadPath(fileName, ImageType.PRODUCT);
        File destinationFile = new File(uploadPath);
        if (!destinationFile.getParentFile().exists())
            destinationFile.getParentFile().mkdir();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(uploadPath);
            fileOutputStream.write(fileByteArray);
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
