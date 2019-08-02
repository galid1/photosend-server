package com.photosend.photosendserver01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PhotosendServer01Application {

    public static void main(String[] args) {
        SpringApplication.run(PhotosendServer01Application.class, args);
    }

}
