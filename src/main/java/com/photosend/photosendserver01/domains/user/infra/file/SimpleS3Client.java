package com.photosend.photosendserver01.domains.user.infra.file;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class SimpleS3Client {
    public AmazonS3 getS3Client(Regions regions) {
        System.out.println(new EnvironmentVariableCredentialsProvider().getCredentials().getAWSAccessKeyId());

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(regions)
                .build();
    }
}
