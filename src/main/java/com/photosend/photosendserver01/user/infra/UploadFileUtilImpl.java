package com.photosend.photosendserver01.user.infra;

import com.photosend.photosendserver01.user.service.UploadFileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class UploadFileUtilImpl implements UploadFileUtil {
    // test
    public static final String ticketsImageFolder = "tickets";
    // test
    public static final String clothesImageFolder = "clothes";



    private String makeFileUploadPath(Long userId, String fileName) {
        return "C:/" + ticketsImageFolder + "/" + String.valueOf(userId) + "_" + makeRandomFileName(fileName);
    }

    private String makeRandomFileName(String originFileName) {
        return UUID.randomUUID() + "_" + originFileName;
    }

    @Override
    public String uploadFile(Long userId, MultipartFile file) {
        String imagePath = makeFileUploadPath(userId, file.getOriginalFilename());

        File destinationFile = new File(imagePath);
        if (!destinationFile.getParentFile().exists())
            destinationFile.getParentFile().mkdir();
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imagePath;
    }
}
