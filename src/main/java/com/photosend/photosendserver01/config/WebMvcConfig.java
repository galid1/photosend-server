package com.photosend.photosendserver01.config;

import com.photosend.photosendserver01.config.interceptor.CheckTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor()).addPathPatterns("/users/{users-id}/**");
    }

    @Bean
    public CheckTokenInterceptor checkTokenInterceptor() {
        return new CheckTokenInterceptor();
    }

}
