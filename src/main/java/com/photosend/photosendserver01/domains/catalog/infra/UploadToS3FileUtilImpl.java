package com.photosend.photosendserver01.domains.catalog.infra;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.photosend.photosendserver01.common.util.aws.SimpleS3Client;
import com.photosend.photosendserver01.domains.catalog.service.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Primary
@RequiredArgsConstructor
public class UploadToS3FileUtilImpl implements FileUtil {
    private final SimpleS3Client simpleS3Client;

    @Value("${photosend.aws.s3.bucket-name.users-upload}")
    private String bucketName;

    @Value("${photosend.aws.s3.upload-path.prefix.users-upload}")
    private String s3Prefix;

    @Override
    public String getFileUploadPathForProductEntity(String filePath) {
        return s3Prefix + filePath;
    }

    // aws path는 목적 파일 명까지 지정 해주어야 함
    @Override
    public String makeFileUploadPath(String fileName, ImageType imageType) {
        return LocalDate.now() + "/" + UUID.randomUUID() + "-" + imageType.getValue() + ".png";
    }

    @Override
    public void uploadFile(String filePath, byte[] fileByteArray) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(MediaType.IMAGE_PNG_VALUE);
        metadata.setContentLength(fileByteArray.length);
        simpleS3Client.getS3Client(Regions.AP_NORTHEAST_2)
                .putObject(
                        new PutObjectRequest(bucketName, filePath, new ByteArrayInputStream(fileByteArray), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                );
        //TODO 비동기로 요청하도록 하고, 실패시 처리로직 구현
    }

    @Override
    public void deleteFile(String filePath) {
        simpleS3Client.getS3Client(Regions.AP_NORTHEAST_2).deleteObject(bucketName, filePath);
    }
}
