package com.photosend.photosendserver01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class PhotosendServer01Application {

    @PostConstruct
    public void setDefaultTimezoneToAsiaSeoul() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("Spring boot application running in UTC timezone :" +new Date());   // It will print UTC timezone
    }

    public static void main(String[] args) {
        SpringApplication.run(PhotosendServer01Application.class, args);
    }

}
