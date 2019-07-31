package com.photosend.photosendserver01.config;

import com.photosend.photosendserver01.config.interceptor.AdminAuthenticationInterceptor;
import com.photosend.photosendserver01.config.interceptor.CheckTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor())
                    .addPathPatterns("/users/{users-id}/**")
                    .addPathPatterns("/orders/**");

        registry.addInterceptor(adminAuthenticationInterceptor())
                    .addPathPatterns("/admin/**");
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
