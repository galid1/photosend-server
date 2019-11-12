package com.photosend.photosendserver01.common.config;

import com.photosend.photosendserver01.common.config.interceptor.AdminAuthenticationInterceptor;
import com.photosend.photosendserver01.common.config.interceptor.CheckTokenInterceptor;
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
//
//        registry.addInterceptor(adminAuthenticationInterceptor())
//                    .addPathPatterns("/admin/{usersId}/products/information");
//        registry.addInterceptor(checkTokenInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/users/");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Bean
    public CheckTokenInterceptor checkTokenInterceptor() {
        return new CheckTokenInterceptor();
    }


    @Bean
    public AdminAuthenticationInterceptor adminAuthenticationInterceptor() {
        return new AdminAuthenticationInterceptor();
    }
}
