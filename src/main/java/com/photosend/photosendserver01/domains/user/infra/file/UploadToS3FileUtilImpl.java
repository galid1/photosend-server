package com.photosend.photosendserver01.domains.user.infra.file;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.photosend.photosendserver01.domains.user.service.FileUtil;
import com.photosend.photosendserver01.util.aws.SimpleS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Primary
public class UploadToS3FileUtilImpl implements FileUtil {
    @Autowired
    private SimpleS3Client simpleS3Client;

    @Value("${photosend.aws.s3.bucket-name}")
    private String bucketName;

    // aws path는 목적 파일 명까지 지정 해주어야 함
    @Override
    public String makeFileUploadPath(String userId, String fileName, ImageType imageType) {
        return LocalDate.now() + "/" + userId + "/" + imageType.getValue() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")) + ".png";
    }

    @Override
    public void uploadFile(String uploadPath, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(MediaType.IMAGE_PNG_VALUE);
        metadata.setContentLength(file.getSize());
        try {
            simpleS3Client.getS3Client(Regions.AP_NORTHEAST_2)
                    .putObject(
                            new PutObjectRequest(bucketName, uploadPath, file.getInputStream(), metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO 비동기로 요청하도록 하고, 실패시 처리로직 구현
    }

    @Override
    public void deleteFile(String filePath) {
        simpleS3Client.getS3Client(Regions.AP_NORTHEAST_2).deleteObject(bucketName, filePath);
    }
}
