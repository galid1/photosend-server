package com.photosend.photosendserver01.common.config;

import com.photosend.photosendserver01.common.config.interceptor.CheckTokenInterceptor;
import com.photosend.photosendserver01.common.util.token.JwtTokenVerifier;
import com.photosend.photosendserver01.common.util.token.JwtTokenVerifierImpl;
import com.photosend.photosendserver01.domains.user.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

}
