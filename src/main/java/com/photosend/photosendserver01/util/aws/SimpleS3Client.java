package com.photosend.photosendserver01.util.aws;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class SimpleS3Client {
    public AmazonS3 getS3Client(Regions regions) {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(regions)
                .build();
    }
}
